package fr.racomach.event.sourcing.command

import fr.racomach.event.sourcing.AggregateId
import fr.racomach.values.Trip
import kotlinx.datetime.LocalTime

typealias CyclistId = AggregateId

sealed class CyclistCommand(
    private val cyclistId: CyclistId,
    override val createCommand: Boolean = false,
) : Command {
    override val id: CyclistId
        get() = cyclistId

    data class Create(val username: String) : CyclistCommand(CyclistId(), createCommand = true)

    data class AddTrip(
        val cyclistId: AggregateId,
        val trip: Trip,
        val roundTripStart: LocalTime? = null,
        val name: String? = null
    ) : CyclistCommand(cyclistId)
}
