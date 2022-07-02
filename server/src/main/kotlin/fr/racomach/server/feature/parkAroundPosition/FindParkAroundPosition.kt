package fr.racomach.server.feature.parkAroundPosition

import arrow.core.Either
import com.mongodb.client.MongoIterable
import fr.racomach.database.park.ParkDatabase
import fr.racomach.database.park.ParkOutput
import fr.racomach.database.park.ParkRepository.Position as ParkPosition

class FindParkAroundPosition(
    private val repository: ParkDatabase,
) {

    data class Param(
        val latitude: Double,
        val longitude: Double,
        val distance: Int,
    )

    fun run(param: Param): Either<Throwable, FindParksResult> =
        repository.nearestParks(ParkPosition(param.latitude, param.longitude), param.distance)
            .map { FindParksResult(it.toResult()) }
}

private fun MongoIterable<ParkOutput>.toResult() = map { park ->
    FindParksResult.ParkResult(
        park.id,
        park.address,
        Position(park.location.latitude, park.location.longitude),
    )
}.toList()