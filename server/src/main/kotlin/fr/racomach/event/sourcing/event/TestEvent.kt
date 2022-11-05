package fr.racomach.event.sourcing.event

import kotlinx.serialization.Serializable

@Serializable
sealed class TestEvent : Event {
    @Serializable
    data class Created(val name: String) : TestEvent()

    @Serializable
    data class AddressChanged(val address: String) : TestEvent()
}


