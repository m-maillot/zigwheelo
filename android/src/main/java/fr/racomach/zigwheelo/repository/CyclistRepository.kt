package fr.racomach.zigwheelo.repository

import arrow.core.Either
import fr.racomach.api.cyclist.CyclistApi
import fr.racomach.api.cyclist.dto.CreateRequest
import fr.racomach.api.error.ErrorResponse
import fr.racomach.zigwheelo.model.Cyclist
import fr.racomach.zigwheelo.model.CyclistId
import fr.racomach.zigwheelo.storage.CyclistStorage
import javax.inject.Inject

interface CyclistRepository {
    suspend fun createCyclist(username: String): Either<ErrorResponse, Cyclist>
}

class CyclistRepositoryImpl @Inject constructor(
    private val api: CyclistApi,
    private val storage: CyclistStorage,
) : CyclistRepository {
    override suspend fun createCyclist(username: String): Either<ErrorResponse, Cyclist> =
        api.create(CreateRequest(username)).map {
            storage.saveCyclist(username, CyclistId(it.cyclistId))
        }
}