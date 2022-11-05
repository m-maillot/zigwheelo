package fr.racomach.event.sourcing

import fr.racomach.event.sourcing.event.Event

interface EventHandler<E : Event> {
    @Suppress("UNCHECKED_CAST")
    fun handle(id: AggregateId, event: Event) =
        on(id, event as E)

    fun on(id: AggregateId, event: E)

    fun supportedEvents(): List<String>
}

inline fun <reified E : Event> EventHandler<E>.getSupportedEvents(): List<String> =
    E::class.sealedSubclasses.map { it.qualifiedName!! }