package fr.racomach.server.util

import arrow.core.Either
import fr.racomach.server.error.HttpError
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.*
import io.ktor.server.response.*

suspend inline fun <reified T : Any> Either<Throwable, T>.toRespond(call: ApplicationCall) = fold(
    { call.handleError(it) },
    { call.respond(it) }
)

suspend fun ApplicationCall.handleError(error: Throwable) = when (error) {
    is HttpError -> respond(error.status, error.content)
    is MissingRequestParameterException -> respond(
        HttpStatusCode.BadRequest,
        "Missing ${error.parameterName} parameter"
    )
    else -> respond(HttpStatusCode.InternalServerError, error.localizedMessage)
}