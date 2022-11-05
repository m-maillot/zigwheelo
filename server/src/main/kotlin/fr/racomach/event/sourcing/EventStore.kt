package fr.racomach.event.sourcing

import fr.racomach.event.sourcing.event.Event
import kotlinx.serialization.json.Json

interface EventStore {
    fun store(id: AggregateId, events: List<Event>, version: Version)
    fun get(id: AggregateId): EventHistory
}

typealias Version = Int

data class EventHistory(val events: List<Event>, val version: Version)

class EventStoreInMemory : EventStore {

    private val eventsStored: HashMap<AggregateId, List<String>> = hashMapOf()

    override fun store(id: AggregateId, events: List<Event>, version: Version) {
        val storedEvents = eventsStored[id] ?: listOf()
        if (storedEvents.size != version) {
            throw WrongVersion(version, storedEvents.size)
        }
        val serializedEvents = events.map { Json.encodeToString(Event.serializer(), it) }
        eventsStored[id] = storedEvents + serializedEvents
    }

    override fun get(id: AggregateId): EventHistory =
        (eventsStored[id] ?: emptyList())
            .map { Json.decodeFromString(Event.serializer(), it) }
            .let { EventHistory(it, it.size) }
}

class WrongVersion(versionProvided: Version, versionExpected: Version) :
    Exception("Version incompatible. Version $versionExpected attendu, reçu version $versionProvided")