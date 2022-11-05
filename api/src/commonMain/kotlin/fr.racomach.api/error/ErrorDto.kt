package fr.racomach.api.error

import kotlinx.serialization.Serializable


@Serializable
data class ErrorResponse(
    val type: String,
    val message: String,
)