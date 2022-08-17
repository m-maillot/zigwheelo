package fr.racomach.api.model

data class Park(
    val id: String,
    val address: String,
    val spots: Int,
    val location: Position,
)