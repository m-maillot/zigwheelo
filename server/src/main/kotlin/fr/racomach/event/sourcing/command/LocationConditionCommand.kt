package fr.racomach.event.sourcing.command

import fr.racomach.event.sourcing.AggregateId
import fr.racomach.values.Location
import fr.racomach.values.Period

sealed class LocationConditionCommand(
    private val locationConditionId: AggregateId,
    override val createCommand: Boolean = false,
) : Command {
    override val id: AggregateId
        get() = locationConditionId

    data class NewLocation(val location: Location) : LocationConditionCommand(AggregateId())

    data class UpdateCondition(
        val locationConditionId: AggregateId,
        val isRaining: Boolean,
        val period: Period,
    ) : LocationConditionCommand(locationConditionId)
}
