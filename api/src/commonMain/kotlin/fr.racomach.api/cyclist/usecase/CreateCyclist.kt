package fr.racomach.api.cyclist.usecase

import com.benasher44.uuid.Uuid
import fr.racomach.api.ZigWheeloApi
import fr.racomach.api.cyclist.dto.CreateRequest
import fr.racomach.api.usecase.Action
import fr.racomach.api.usecase.Effect
import fr.racomach.api.usecase.State
import fr.racomach.api.usecase.Store
import io.github.aakira.napier.Napier
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed class CreateCyclistState : State {
    object Pending : CreateCyclistState()
    object Creating : CreateCyclistState()
    data class Created(val id: Uuid) : CreateCyclistState()
    data class Error(val type: String, val detail: String) : CreateCyclistState()
}

sealed class CreateCyclistAction : Action {
    data class Create(val username: String) :
        CreateCyclistAction()

    data class Created(val id: Uuid) : CreateCyclistAction()
    data class Error(val type: String, val detail: String) : CreateCyclistAction()
}

sealed class CreateCyclistEffect : Effect {
    data class Error(val error: Exception) : CreateCyclistEffect()
}

class CreateCyclist(
    private val zigWheeloApi: ZigWheeloApi
) : Store<CreateCyclistState, CreateCyclistAction, CreateCyclistEffect>,
    CoroutineScope by CoroutineScope(Dispatchers.Main) {

    private val state = MutableStateFlow<CreateCyclistState>(CreateCyclistState.Pending)
    private val sideEffect = MutableSharedFlow<CreateCyclistEffect>()

    override fun observeState(): StateFlow<CreateCyclistState> = state

    override fun observeSideEffect(): Flow<CreateCyclistEffect> = sideEffect

    override fun dispatch(action: CreateCyclistAction) {
        Napier.v(tag = "CreateCyclist", message = "Action: $action")
        val oldState = state.value
        val newState: CreateCyclistState = when (action) {
            is CreateCyclistAction.Create -> {
                launch { createCyclist(action.username) }
                CreateCyclistState.Creating
            }
            is CreateCyclistAction.Created -> {
                CreateCyclistState.Created(action.id)
            }
            is CreateCyclistAction.Error -> {
                CreateCyclistState.Error(action.type, action.detail)
            }
        }
        if (newState != oldState) {
            state.value = newState
        }
    }

    private suspend fun createCyclist(username: String) {
        try {
            val response = zigWheeloApi.cyclist.create(CreateRequest(username))

            dispatch(response.fold({
                CreateCyclistAction.Error(it.type, it.message)
            }, {
                CreateCyclistAction.Created(it.cyclistId)
            }))
        } catch (e: Exception) {
            dispatch(
                CreateCyclistAction.Error(
                    "fatal",
                    e.message ?: "unknown"
                )
            )
        }
    }
}