package fr.racomach.server.feature.parkAroundPosition

import arrow.core.Either
import fr.racomach.api.response.FindParkResult
import fr.racomach.api.response.FindParksResult
import fr.racomach.api.response.PositionResult
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

    suspend fun run(param: Param): Either<Throwable, FindParksResult> =
        repository.nearestParks(ParkPosition(param.latitude, param.longitude), param.distance)
            .map { FindParksResult(it.toResult()) }
}

private fun List<ParkOutput>.toResult() = map { park ->
    FindParkResult(
        park.id.toString(),
        park.address,
        park.spot,
        PositionResult(park.location.latitude, park.location.longitude),
    )
}.toList()