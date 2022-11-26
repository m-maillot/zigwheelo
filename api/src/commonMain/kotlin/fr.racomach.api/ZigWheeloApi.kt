package fr.racomach.api

import arrow.core.Either
import fr.racomach.api.error.ErrorResponse
import fr.racomach.api.onboard.api.OnboardApi
import fr.racomach.api.onboard.api.OnboardApiImpl
import fr.racomach.api.place.PlaceApi
import fr.racomach.api.place.PlaceApiImpl
import fr.racomach.api.response.FindParksResult
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*

class ZigWheeloApi internal constructor(
    private val client: HttpClient,
    private val baseUrl: String,
    val onboard: OnboardApi = OnboardApiImpl(client, baseUrl),
    val place: PlaceApi = PlaceApiImpl(client, baseUrl),
) {
    suspend fun searchParks(latitude: Double, longitude: Double, distance: Int) =
        client.get("$baseUrl/api/parks/search") {
            parameter("latitude", latitude)
            parameter("longitude", longitude)
            parameter("distance", distance)
        }.body<FindParksResult>()

    companion object
}

suspend inline fun <reified T> Result<HttpResponse>.toEither(): Either<ErrorResponse, T> =
    if (this.isSuccess) {
        val response = getOrThrow()
        if (response.status == HttpStatusCode.OK) {
            Either.Right(response.body())
        } else {
            Either.Left(response.body())
        }
    } else {
        Either.Left(ErrorResponse("NETWORK", exceptionOrNull()?.message ?: "erreur inconnue"))
    }
