package fr.racomach.server.util

import arrow.core.Either
import fr.racomach.api.error.ErrorResponse
import fr.racomach.server.error.HttpError
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.*
import io.ktor.server.response.*

suspend inline fun <reified T : Any> Either<Throwable, T>.toRespond(call: ApplicationCall) = fold(
    { call.handleError(it) },
    { call.respond(it) }
)

suspend inline fun <reified T : Any> Either<Throwable, T>.toRespondAccepted(call: ApplicationCall) =
    fold(
        { call.handleError(it) },
        { call.respond(HttpStatusCode.Accepted) }
    )

suspend fun ApplicationCall.handleError(error: Throwable) = when (error) {
    is HttpError -> respond(error.status, error.content)
    is MissingRequestParameterException -> respond(
        HttpStatusCode.BadRequest,
        "Missing ${error.parameterName} parameter"
    )

    else -> respond(
        HttpStatusCode.InternalServerError,
        ErrorResponse(
            error::class.java.simpleName,
            error.localizedMessage ?: error.message ?: "erreur inconnue"
        )
    )
}
