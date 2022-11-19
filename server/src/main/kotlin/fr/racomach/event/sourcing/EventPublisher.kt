package fr.racomach.event.sourcing

import fr.racomach.event.sourcing.event.Event

fun interface EventPublisher {
    fun publish(id: AggregateId, events: List<Event>, version: Version)
}

class EventPublisherImpl(
    private val eventStore: EventStore,
    private val eventHandlers: List<EventHandler<*>>
) : EventPublisher {

    override fun publish(id: AggregateId, events: List<Event>, version: Version) {
        eventStore.store(id, events, version)
        events.forEach { event ->
            val handlers = findHandlers(event)
            handlers.forEach { it.handle(id, event) }
        }
    }

    private fun findHandlers(event: Event): List<EventHandler<*>> =
        eventHandlers.filter { it.supportedEvents().startWith(event::class.qualifiedName) }

    private fun List<String>.startWith(value: String?) = value?.let { any { value.startsWith(it) } } ?: false
}