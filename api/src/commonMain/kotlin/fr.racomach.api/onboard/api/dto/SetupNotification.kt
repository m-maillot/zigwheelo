package fr.racomach.api.onboard.api.dto

import kotlinx.datetime.LocalTime
import kotlinx.serialization.Serializable

@Serializable
data class SetupNotificationRequest(
    val token: String? = null,
    val notificationAt: LocalTime? = null,
)

