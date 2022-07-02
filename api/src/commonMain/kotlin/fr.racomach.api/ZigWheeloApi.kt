package fr.racomach.api

import fr.racomach.api.response.FindParksResult
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*

class ZigWheeloApi internal constructor(
    private val client: HttpClient,
    private val baseUrl: String,
) {
    suspend fun searchParks(latitude: Double, longitude: Double, distance: Int) =
        client.get("$baseUrl/api/parks/search") {
            parameter("latitude", latitude)
            parameter("longitude", longitude)
            parameter("distance", distance)
        }.body<FindParksResult>()

    companion object
}