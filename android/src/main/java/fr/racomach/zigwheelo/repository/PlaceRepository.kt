package fr.racomach.zigwheelo.repository

import arrow.core.Either
import fr.racomach.api.common.Location
import fr.racomach.api.error.ErrorResponse
import fr.racomach.api.place.PlaceApi
import javax.inject.Inject

interface PlaceRepository {
    data class Place(val label: String, val location: Location)

    suspend fun search(query: String, location: Location): Either<ErrorResponse, List<Place>>
}

class PlaceRepositoryNetwork @Inject constructor(
    private val api: PlaceApi,
) : PlaceRepository {
    override suspend fun search(
        query: String,
        location: Location
    ): Either<ErrorResponse, List<PlaceRepository.Place>> {
        return api.search(query, location)
            .map { it.places.map { place -> PlaceRepository.Place(place.label, place.location) } }
    }

}