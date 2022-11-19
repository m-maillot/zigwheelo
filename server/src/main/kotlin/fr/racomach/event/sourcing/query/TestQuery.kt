package fr.racomach.event.sourcing.query

sealed class TestQuery : Query {
    data class Test(val data: String) : TestQuery()
}


