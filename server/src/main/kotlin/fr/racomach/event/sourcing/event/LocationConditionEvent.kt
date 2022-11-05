package fr.racomach.event.sourcing.event

import fr.racomach.values.Location
import fr.racomach.values.Period
import kotlinx.serialization.Serializable

@Serializable
sealed class LocationConditionEvent : Event {
    @Serializable
    data class LocationCreated(val location: Location) :
        LocationConditionEvent()

    @Serializable
    data class NewConditionReceived(val isRaining: Boolean, val period: Period) :
        LocationConditionEvent()
}


