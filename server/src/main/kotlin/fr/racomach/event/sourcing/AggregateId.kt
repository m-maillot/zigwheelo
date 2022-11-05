package fr.racomach.event.sourcing

import com.benasher44.uuid.Uuid

@JvmInline
value class AggregateId(val id: Uuid = Uuid.randomUUID()) {
    constructor(id: String) : this(Uuid.fromString(id))

    override fun toString() = id.toString()
}
