package fr.racomach.zigwheelo.onboarding.usecase

import arrow.core.Either
import arrow.core.flatMap
import fr.racomach.api.error.ErrorResponse
import fr.racomach.zigwheelo.repository.CyclistRepository
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class RegisterUsername(
    private val cyclistRepo: CyclistRepository,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
) {

    @Inject
    constructor(cyclistRepo: CyclistRepository) : this(cyclistRepo, Dispatchers.IO)

    fun run() = flow {
        var error: String? = null
        do {
            val deferred = CompletableDeferred<String?>()
            emit(State.AskUsername(deferred, error))
            val username = deferred.await()
            emit(State.Validating)
            val result = validate(username).flatMap { cyclistRepo.createCyclist(it) }
            error = when (result) {
                is Either.Left -> result.value.message
                is Either.Right -> null
            }
        } while (result.isLeft())
        emit(State.Succeed)
    }.flowOn(dispatcher)

    private fun validate(username: String?): Either<ErrorResponse, String> {
        if (username == null) {
            return Either.Left(ErrorResponse("EMPTY", "Veuillez indiquer un pseudo"))
        }
        if (username.length <= 3) {
            return Either.Left(
                ErrorResponse(
                    "TOO_SHORT",
                    "Votre pseudo doit contenir au moins 3 charactères"
                )
            )
        }
        return Either.Right(username)
    }

    sealed class State {
        data class AskUsername(
            val callback: CompletableDeferred<String?>,
            val error: String? = null
        ) : State()

        object Validating : State()
        object Succeed : State()
    }
}