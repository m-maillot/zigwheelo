package fr.racomach.event.sourcing

import fr.racomach.event.sourcing.command.*
import fr.racomach.server.feature.cyclist.CyclistAggregate
import fr.racomach.server.feature.locationCondition.LocationConditionAggregate

fun interface AggregateSelector {
    fun select(command: Command): Aggregate<*, *>
}

val selector: AggregateSelector = AggregateSelector {
    when (it) {
        is TestCommand -> throw IllegalArgumentException("Test command is only for test purpose")
        is CyclistCommand -> CyclistAggregate()
        is LocationConditionCommand -> LocationConditionAggregate()
    }
}