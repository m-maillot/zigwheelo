package fr.racomach.event.sourcing

import arrow.core.Either
import arrow.core.right
import fr.racomach.event.sourcing.query.TestQuery
import io.kotest.assertions.arrow.core.shouldBeRight
import io.kotest.core.spec.style.FunSpec

class QueryPublisherSpecs : FunSpec({
    test("Quand je passe une query, j'attends à appeler le handler associé et retourner la donnée fournie par celui ci") {
        val handler = object : QueryHandler<TestQuery.Test> {
            override suspend fun on(query: TestQuery.Test): Either<Error, Any> {
                return query.data.right()
            }
        }
        val queryPublisher = QueryPublisherImpl {
            when (it) {
                is TestQuery.Test -> handler
                else -> error("Unexpected event in test")
            }
        }
        val response = queryPublisher.on(TestQuery.Test("Hello world !"))

        response shouldBeRight "Hello world !"
    }
})