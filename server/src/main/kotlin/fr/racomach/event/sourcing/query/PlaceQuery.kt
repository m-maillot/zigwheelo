package fr.racomach.event.sourcing.query

import fr.racomach.values.Location
import fr.racomach.values.Trip
import kotlinx.datetime.LocalTime
import kotlinx.serialization.Serializable

sealed class PlaceQuery : Query {
    data class Search(val location: Location?, val query: String) : PlaceQuery()
}


