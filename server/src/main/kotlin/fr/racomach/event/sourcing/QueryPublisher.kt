package fr.racomach.event.sourcing

import arrow.core.Either
import fr.racomach.event.sourcing.query.Query

fun interface QueryPublisher {
    suspend fun on(query: Query): Either<Error, Any>
}

class QueryPublisherImpl(
    private val selector: QueryHandlerSelector
) : QueryPublisher {
    override suspend fun on(query: Query) =
        selector.select(query).handle(query)
}