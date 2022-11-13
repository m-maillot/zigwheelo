package fr.racomach.api.cyclist

import arrow.core.Either
import fr.racomach.api.cyclist.dto.AddTripRequest
import fr.racomach.api.cyclist.dto.AddTripResponse
import fr.racomach.api.cyclist.dto.CreateRequest
import fr.racomach.api.cyclist.dto.CreateResponse
import fr.racomach.api.error.ErrorResponse
import fr.racomach.api.toEither
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.http.*

interface CyclistApi {
    suspend fun create(request: CreateRequest): Either<ErrorResponse, CreateResponse>
    suspend fun addTrip(request: AddTripRequest): Either<ErrorResponse, AddTripResponse>
}

class CyclistApiImpl internal constructor(
    private val client: HttpClient,
    private val baseUrl: String,
) : CyclistApi {

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
}

