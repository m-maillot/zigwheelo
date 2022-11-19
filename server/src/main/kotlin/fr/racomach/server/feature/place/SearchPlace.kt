package fr.racomach.server.feature.place

import arrow.core.Either
import fr.racomach.api.place.dto.SearchResponse
import fr.racomach.event.sourcing.Error
import fr.racomach.event.sourcing.QueryHandler
import fr.racomach.event.sourcing.query.PlaceQuery
import fr.racomach.server.error.DomainError
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.serialization.Serializable

class SearchPlace(
    private val httpClient: HttpClient,
) : QueryHandler<PlaceQuery.Search> {

    override suspend fun on(query: PlaceQuery.Search): Either<Error, Any> =
        runCatching {
            httpClient.get("https://api-adresse.data.gouv.fr/search") {
                contentType(type = ContentType.Application.Json)
                url {
                    query.location?.apply {
                        parameters.append("lat", latitude.toString())
                        parameters.append("lon", longitude.toString())
                    }
                    parameters.append("q", query.query)
                }
            }.body<ApiResponse>()
        }.fold(
            { Either.Right(it.toOutput()) },
            { Either.Left(DomainError.Place.Network(it.localizedMessage)) }
        )

    private fun ApiResponse.toOutput() = SearchResponse(places = features.map {
        SearchResponse.Place(
            fr.racomach.api.common.Location(
                longitude = it.geometry.coordinates[0],
                latitude = it.geometry.coordinates[1]
            ), it.properties.label, it.properties.score
        )
    })

    @Serializable
    private data class ApiResponse(
        val type: String,
        val version: String,
        val features: List<Feature>,
        val attribution: String,
        val licence: String,
        val query: String,
        val limit: Int,
    ) {
        @Serializable
        data class Feature(
            val type: String,
            val geometry: Geometry,
            val properties: Properties,
        ) {
            @Serializable
            data class Geometry(
                val type: String,
                val coordinates: List<Double>,
            )

            @Serializable
            data class Properties(
                val label: String,
                val score: Double,
            )
        }
    }
}