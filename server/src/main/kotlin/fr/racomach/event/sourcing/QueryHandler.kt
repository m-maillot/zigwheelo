package fr.racomach.event.sourcing

import arrow.core.Either
import fr.racomach.event.sourcing.query.Query

interface QueryHandler<Q : Query> {
    @Suppress("UNCHECKED_CAST")
    suspend fun handle(query: Query) =
        on(query as Q)

    suspend fun on(query: Q): Either<Error, Any>
}