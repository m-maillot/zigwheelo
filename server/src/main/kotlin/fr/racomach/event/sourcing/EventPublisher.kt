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

    private fun findHandlers(event: Event): List<EventHandler<*>> {
        val handlers = eventHandlers.filter { it.supportedEvents().startWith(event::class.qualifiedName) }
        if (handlers.size > 1) {
            throw IllegalStateException("Multiple aggregates listening command ${event::class.qualifiedName}")
        }
        return handlers
    }

    private fun List<String>.startWith(value: String?) = value?.let { any { value.startsWith(it) } } ?: false
}