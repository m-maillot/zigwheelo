package fr.racomach.zigwheelo.model

import com.benasher44.uuid.Uuid

@JvmInline
value class CyclistId(val id: Uuid) {
    constructor(id: String) : this(Uuid.fromString(id))

    override fun toString() = id.toString()
}

data class Cyclist(
    val id: CyclistId,
    val username: String
)