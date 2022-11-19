package fr.racomach.event.sourcing

import fr.racomach.event.sourcing.query.PlaceQuery
import fr.racomach.event.sourcing.query.Query
import fr.racomach.event.sourcing.query.TestQuery
import fr.racomach.server.feature.place.SearchPlace
import org.koin.core.module.Module

fun interface QueryHandlerSelector {
    fun select(query: Query): QueryHandler<*>
}

fun Module.querySelector() {
    single {
        QueryHandlerSelector {
            when (it) {
                is TestQuery -> throw IllegalArgumentException("Test command is only for test purpose")
                is PlaceQuery.Search -> SearchPlace(get())
            }
        }
    }

}