package fr.racomach.server.feature.locationCondition

import arrow.core.Either
import fr.racomach.event.sourcing.Aggregate
import fr.racomach.event.sourcing.Error
import fr.racomach.event.sourcing.command.LocationConditionCommand
import fr.racomach.event.sourcing.command.LocationConditionCommand.NewLocation
import fr.racomach.event.sourcing.command.LocationConditionCommand.UpdateCondition
import fr.racomach.event.sourcing.event.LocationConditionEvent
import fr.racomach.event.sourcing.event.LocationConditionEvent.LocationCreated
import fr.racomach.event.sourcing.event.LocationConditionEvent.NewConditionReceived

class LocationConditionAggregate : Aggregate<LocationConditionEvent, LocationConditionCommand> {

    override fun apply(
        history: List<LocationConditionEvent>,
        command: LocationConditionCommand
    ): Either<Error, List<LocationConditionEvent>> =
        when (command) {
            is NewLocation -> apply(command)
            is UpdateCondition -> apply(command)
        }

    private fun apply(command: NewLocation) =
        Either.Right(
            listOf(
                LocationCreated(
                    command.location,
                )
            )
        )

    private fun apply(command: UpdateCondition) =
        Either.Right(
            listOf(
                NewConditionReceived(
                    command.isRaining,
                    command.period,
                )
            )
        )
}