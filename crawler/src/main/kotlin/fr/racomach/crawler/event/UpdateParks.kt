package fr.racomach.crawler.event

import arrow.core.Either
import arrow.core.flatMap
import fr.racomach.crawler.provider.grandlyon.BikeParksResponse
import fr.racomach.crawler.provider.grandlyon.GrandLyonApi
import fr.racomach.database.park.ParkInput
import fr.racomach.database.park.ParkRepository

class UpdateParks(
    private val grandLyonApi: GrandLyonApi,
    private val parkRepository: ParkRepository,
) {

    suspend fun run(): Either<Throwable, ParkRepository.UpsertResult> =
        grandLyonApi.bikeParks()
            .flatMap { data -> parkRepository.upsertParks(data.features.map { it.toParkInput() }) }

    private fun BikeParksResponse.Feature.toParkInput() = ParkInput(
        externalId = properties.nom,
        spot = properties.capacite ?: -1,
        sheltered = properties.abrite == "Oui",
        place = properties.localisation,
        address = properties.adresse,
        city = properties.commune,
        longitude = geometry.coordinates[0],
        latitude = geometry.coordinates[1],
    )
}