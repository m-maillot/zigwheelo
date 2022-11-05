package fr.racomach.event.sourcing

import fr.racomach.event.sourcing.event.Event
import java.util.concurrent.atomic.AtomicInteger

class EventStoreStub(
    private val getResponse: EventHistory? = null
) : EventStore {
    val storeHasBeenCalled: AtomicInteger = AtomicInteger(0)
    val getHasBeenCalled: AtomicInteger = AtomicInteger(0)

    override fun store(id: AggregateId, events: List<Event>, version: Version) {
        storeHasBeenCalled.incrementAndGet()
    }

    override fun get(id: AggregateId): EventHistory {
        getHasBeenCalled.incrementAndGet()
        if (getResponse == null) {
            TODO()
        }
        return getResponse
    }
}