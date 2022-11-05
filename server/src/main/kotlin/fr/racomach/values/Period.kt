package fr.racomach.values

import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable

@Serializable
data class Period(
    val startAt: Instant,
    val endAt: Instant,
)
