package fr.racomach.api.cyclist.dto

import com.benasher44.uuid.Uuid
import fr.racomach.api.serializer.UuidSerializer
import kotlinx.serialization.Serializable

@Serializable
data class CreateRequest(
    val username: String,
)

@Serializable
data class CreateResponse(
    @Serializable(with = UuidSerializer::class)
    val cyclistId: Uuid,
)
