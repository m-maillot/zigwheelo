package fr.racomach.event.sourcing

import fr.racomach.event.sourcing.event.Event

class EventPublisherMock : EventPublisher {
    private val _calls: MutableList<Triple<AggregateId, List<Event>, Version>> = mutableListOf()
    val calls: List<Triple<AggregateId, List<Event>, Version>>
        get() = _calls

    override fun publish(id: AggregateId, events: List<Event>, version: Version) {
        _calls.add(Triple(id, events, version))
    }
}