package fr.racomach.api.onboard.api

import arrow.core.Either
import fr.racomach.api.error.ErrorResponse
import fr.racomach.api.onboard.api.dto.*
import fr.racomach.api.toEither
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.http.*

interface OnboardApi {
    suspend fun create(request: CreateRequest): Either<ErrorResponse, CreateResponse>
    suspend fun addTrip(request: AddTripRequest): Either<ErrorResponse, AddTripResponse>
    suspend fun setupNotification(request: SetupNotificationRequest): Either<ErrorResponse, Unit>
}

class OnboardApiImpl internal constructor(
    private val client: HttpClient,
    private val baseUrl: String,
) : OnboardApi {

    override suspend fun create(request: CreateRequest): Either<ErrorResponse, CreateResponse> =
        runCatching {
            client.post("$baseUrl/api/v1/cyclist/create") {
                contentType(type = ContentType.Application.Json)
                setBody(request)
            }
        }.toEither()

    override suspend fun addTrip(request: AddTripRequest): Either<ErrorResponse, AddTripResponse> =
        runCatching {
            client.post("$baseUrl/api/v1/cyclist/trip") {
                contentType(type = ContentType.Application.Json)
                setBody(request)
            }
        }.toEither()

    override suspend fun setupNotification(request: SetupNotificationRequest): Either<ErrorResponse, Unit> =
        runCatching {
            client.post("$baseUrl/api/v1/cyclist/notification") {
                contentType(type = ContentType.Application.Json)
                setBody(request)
            }
        }.toEither()
}

