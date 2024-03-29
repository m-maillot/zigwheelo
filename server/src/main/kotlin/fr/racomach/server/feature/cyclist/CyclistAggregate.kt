package fr.racomach.server.feature.cyclist

import arrow.core.Either
import arrow.core.right
import fr.racomach.event.sourcing.Aggregate
import fr.racomach.event.sourcing.Error
import fr.racomach.event.sourcing.command.CyclistCommand
import fr.racomach.event.sourcing.event.CyclistEvent
import fr.racomach.server.error.DomainError
import fr.racomach.values.Trip

class CyclistAggregate : Aggregate<CyclistEvent, CyclistCommand> {

    override fun apply(
        history: List<CyclistEvent>,
        command: CyclistCommand
    ): Either<Error, List<CyclistEvent>> =
        DecisionProjection(history).let { projection ->
            when (command) {
                is CyclistCommand.AddTrip -> apply(command, projection)
                is CyclistCommand.Create -> apply(history, command)
                is CyclistCommand.SetupNotification -> apply(command)
                is CyclistCommand.SendNotification -> apply(command, projection)
            }
        }

    private fun apply(
        history: List<CyclistEvent>,
        command: CyclistCommand.Create
    ): Either<Error, List<CyclistEvent>> =
        if (history.isNotEmpty())
            Either.Left(DomainError.Cyclist.AlreadyCreated)
        else
            Either.Right(listOf(CyclistEvent.Created(command.username)))


    private fun apply(
        command: CyclistCommand.AddTrip,
        projection: DecisionProjection
    ): Either<Error, List<CyclistEvent>> {
        val overlapTrip = projection.trips.find { it.overlap(command.trip) }
        if (overlapTrip != null) {
            return Either.Left(DomainError.Cyclist.DuplicateTrip(overlapTrip.schedule))
        }
        return Either.Right(
            listOf(CyclistEvent.TripAdded(command.trip, command.roundTripStart, command.name))
        )
    }

    private fun apply(command: CyclistCommand.SetupNotification): Either<Error, List<CyclistEvent>> {
        if (command.token != null && command.notificationAt != null) {
            return listOf(
                CyclistEvent.NotificationSettingsUpdated(
                    command.token,
                    command.notificationAt
                )
            ).right()
        }
        return listOf(CyclistEvent.NotificationSettingsRemoved).right()
    }

    private fun apply(
        command: CyclistCommand.SendNotification,
        projection: DecisionProjection
    ): Either<Error, List<CyclistEvent>> =
        projection.notificationToken?.let { token ->
            listOf(CyclistEvent.NotificationReady(token, command.name)).right()
        } ?: emptyList<CyclistEvent>().right()


    private fun Trip.overlap(trip: Trip): Boolean {
        val start = schedule.hour * 60 + schedule.minute
        val end = start + duration.inWholeMinutes

        val otherStart = trip.schedule.hour * 60 + schedule.minute
        val otherEnd = otherStart + trip.duration.inWholeMinutes

        return otherStart in start..end || otherEnd in start..end || start in otherStart..otherEnd || end in otherStart..otherEnd
    }

    private class DecisionProjection(history: List<CyclistEvent>) {

        val trips: MutableList<Trip> = mutableListOf()
        var notificationToken: String? = null
            private set

        init {
            apply(history)
        }

        fun apply(history: List<CyclistEvent>) {
            history.forEach {
                when (it) {
                    is CyclistEvent.TripAdded -> apply(it)
                    is CyclistEvent.Created -> {}
                    is CyclistEvent.NotificationSettingsUpdated -> {
                        notificationToken = it.token
                    }
                    CyclistEvent.NotificationSettingsRemoved -> {
                        notificationToken = null
                    }
                    is CyclistEvent.NotificationReady -> {}
                }
            }
        }

        fun apply(event: CyclistEvent.TripAdded) {
            trips.add(event.trip)
            if (event.roundSchedule != null) {
                trips.add(
                    Trip(
                        event.trip.to,
                        event.trip.from,
                        event.trip.schedule,
                        event.trip.duration
                    )
                )
            }
        }
    }
}