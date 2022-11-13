package fr.racomach.zigwheelo.repository

import arrow.core.Either
import fr.racomach.api.cyclist.CyclistApi
import fr.racomach.api.cyclist.dto.CreateRequest
import fr.racomach.api.cyclist.dto.CreateResponse
import fr.racomach.api.error.ErrorResponse
import javax.inject.Inject

interface CyclistRepository {
    suspend fun createCyclist(username: String): Either<ErrorResponse, CreateResponse>
}

class CyclistRepositoryImpl @Inject constructor(
    private val api: CyclistApi
) : CyclistRepository {
    override suspend fun createCyclist(username: String): Either<ErrorResponse, CreateResponse> =
        api.create(CreateRequest(username))
}