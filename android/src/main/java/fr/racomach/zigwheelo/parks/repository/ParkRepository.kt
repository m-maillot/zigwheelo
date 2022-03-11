package fr.racomach.zigwheelo.parks.repository

import arrow.core.Either
import fr.racomach.api.ZigWheeloApi
import fr.racomach.api.parks.FindParksResult
import fr.racomach.zigwheelo.parks.model.Park
import fr.racomach.zigwheelo.parks.model.Position
import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.plugins.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json

interface ParkRepository {

    suspend fun searchParks(
        latitude: Double,
        longitude: Double,
        distance: Int
    ): Either<Throwable, List<Park>>

}

class ParkNetwork : ParkRepository {
    val api = ZigWheeloApi(HttpClient(Android) {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                prettyPrint = true
                isLenient = true
            })
        }
    }, "http://192.168.1.13:8080")

    override suspend fun searchParks(
        latitude: Double,
        longitude: Double,
        distance: Int
    ): Either<Throwable, List<Park>> = withContext(Dispatchers.IO) {
        Either.catch { api.searchParks(latitude, longitude, distance) }
            .map { it.toParks() }
    }


    private fun FindParksResult.toParks() = park.map {
        Park(it.id, it.address, Position(it.location.latitude, it.location.longitude))
    }
}