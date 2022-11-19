package fr.racomach.api.place

import arrow.core.Either
import fr.racomach.api.common.Location
import fr.racomach.api.error.ErrorResponse
import fr.racomach.api.place.dto.SearchResponse
import fr.racomach.api.toEither
import io.ktor.client.*
import io.ktor.client.request.*

interface PlaceApi {
    suspend fun search(
        query: String,
        location: Location? = null
    ): Either<ErrorResponse, SearchResponse>
}

class PlaceApiImpl internal constructor(
    private val client: HttpClient,
    private val baseUrl: String,
) : PlaceApi {

    override suspend fun search(
        query: String,
        location: Location?
    ): Either<ErrorResponse, SearchResponse> =
        runCatching {
            client.get("$baseUrl/api/v1/places/search") {
                url {
                    location?.apply {
                        parameters.append("latitude", latitude.toString())
                        parameters.append("longitude", longitude.toString())
                    }
                    parameters.append("query", query)
                }
            }
        }.toEither()
}

