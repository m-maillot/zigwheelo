package fr.racomach.server.feature.place

import fr.racomach.api.place.dto.SearchResponse
import fr.racomach.event.sourcing.query.PlaceQuery
import fr.racomach.server.util.createHttpClient
import fr.racomach.values.Location
import io.kotest.assertions.arrow.core.shouldBeLeft
import io.kotest.assertions.arrow.core.shouldBeRight
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldContain
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.instanceOf

class SearchPlaceSpecs: FunSpec({

    test("Lorsque je recherche l'adresse, 6 rue lamothe, avec la lat/long de lyon, je dois trouver l'adresse 6 rue lamothe, 69007 Lyon dans les résultats") {
        val searchPlace = SearchPlace(createHttpClient())
        val response = searchPlace.on(PlaceQuery.Search(query = "6 rue lamothe", location = Location(latitude = 45.7542224, longitude = 4.8386116)))
        response shouldBeRight instanceOf<SearchResponse>()
        response.tap {
            (it as SearchResponse).places.first().label shouldBe "6 Rue Lamothe 69007 Lyon"
        }

    }
})