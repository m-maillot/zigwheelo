package fr.racomach.api.model

import fr.racomach.api.generic.Position

data class Park(
    val id: String,
    val address: String,
    val spots: Int,
    val location: Position,
)