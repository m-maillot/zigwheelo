package fr.racomach.server.feature.cyclist

import fr.racomach.event.sourcing.AggregateId
import fr.racomach.event.sourcing.command.CyclistCommand
import fr.racomach.event.sourcing.event.CyclistEvent
import fr.racomach.server.error.DomainError
import fr.racomach.server.feature.cyclist.dataset.generateTrip
import io.kotest.assertions.arrow.core.shouldBeLeft
import io.kotest.assertions.arrow.core.shouldBeRight
import io.kotest.core.spec.style.FunSpec
import kotlinx.datetime.LocalTime
import kotlin.time.Duration.Companion.minutes

class CyclistAggregateSpecs : FunSpec({
    val aggregate = CyclistAggregate()
    val aggregateId = AggregateId()

    test("A la création d'un cycliste, on doit avoir un évènement cycliste créé") {
        val command = CyclistCommand.Create("john")
        val expectedEvents = listOf(CyclistEvent.Created("john"))

        val response = aggregate.apply(emptyList(), command)

        response shouldBeRight expectedEvents
    }

    test("A la création d'un trip sans retour, on doit avoir un évènement trip created") {
        val trip = generateTrip()
        val command = CyclistCommand.AddTrip(aggregateId, trip)
        val expectedEvent = listOf(
            CyclistEvent.TripAdded(trip, null, null)
        )
        val response = aggregate.apply(emptyList(), command)

        response shouldBeRight expectedEvent
    }

    test("A la création d'un avec retour, on doit avoir 1 évènement trip created") {
        val trip = generateTrip()
        val command = CyclistCommand.AddTrip(aggregateId, trip, LocalTime(17, 30))
        val expectedEvent = listOf(
            CyclistEvent.TripAdded(trip, LocalTime(17, 30), null)
        )
        val response = aggregate.apply(emptyList(), command)

        response shouldBeRight expectedEvent
    }

    test("A la création d'un trip, on doit retourner une erreur si on a un autre trip sur la même période") {
        val trip1 = generateTrip(schedule = LocalTime(8, 30), duration = 20.minutes)
        val trip2 = generateTrip(schedule = LocalTime(8, 0), duration = 40.minutes)
        val command = CyclistCommand.AddTrip(aggregateId, trip1)
        val eventHistory = listOf(
            CyclistEvent.TripAdded(trip2, null, null),
        )

        val response = aggregate.apply(eventHistory, command)

        response shouldBeLeft DomainError.Cyclist.DuplicateTrip(trip2.schedule)
    }
})