package fr.racomach.server.error

import io.ktor.http.*

open class HttpError(val status: HttpStatusCode, val content: Any) : Exception()

data class InvalidInput(val data: Any) : HttpError(HttpStatusCode.BadRequest, data)