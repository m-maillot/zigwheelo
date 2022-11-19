package fr.racomach.server.feature.place

import arrow.core.Either
import arrow.core.flatMap
import fr.racomach.event.sourcing.QueryPublisher
import fr.racomach.event.sourcing.query.PlaceQuery
import fr.racomach.server.util.toRespond
import fr.racomach.values.Location
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.routing.*
import io.ktor.server.util.*

fun Route.place(
    queryPublisher: QueryPublisher,
) {
    authenticate("custom") {
        get("/places/search") {
            extractParam(call)
                .flatMap { queryPublisher.on(it) }
                .toRespond(call)
        }
    }
}

private fun extractParam(call: ApplicationCall): Either<Throwable, PlaceQuery.Search> =
    Either.catch {
        PlaceQuery.Search(
            query = call.parameters.getOrFail("query"),
            location = if (call.parameters["latitude"] != null) {
                Location(
                    latitude = call.parameters.getOrFail("latitude").toDouble(),
                    longitude = call.parameters.getOrFail("longitude").toDouble(),
                )
            } else {
                null
            }
        )
    }