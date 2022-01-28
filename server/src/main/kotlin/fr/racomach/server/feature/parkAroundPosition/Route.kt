package fr.racomach.server.feature.parkAroundPosition

import arrow.core.Either
import arrow.core.flatMap
import fr.racomach.server.util.toRespond
import io.ktor.server.application.*
import io.ktor.server.routing.*
import io.ktor.server.util.*

fun Application.findParkAroundPosition(
    findParkAroundPosition: FindParkAroundPosition,
) {
    routing {
        get("/api/parks/search") {
            extractParam(call)
                .flatMap { findParkAroundPosition.run(it) }
                .toRespond(call)
        }
    }
}

private fun extractParam(call: ApplicationCall): Either<Throwable, FindParkAroundPosition.Param> =
    Either.catch {
        FindParkAroundPosition.Param(
            call.parameters.getOrFail("latitude").toDouble(),
            call.parameters.getOrFail("longitude").toDouble(),
            call.parameters.getOrFail("distance").toInt(),
        )
    }