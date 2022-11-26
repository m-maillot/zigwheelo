package fr.racomach.api.onboard.usecase

import com.benasher44.uuid.Uuid
import fr.racomach.api.ZigWheeloApi
import fr.racomach.api.common.Location
import fr.racomach.api.onboard.api.dto.AddTripRequest
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
import kotlinx.datetime.LocalTime
import kotlin.time.Duration

sealed class AddTripState : State {
    object Pending : AddTripState()
    object Creating : AddTripState()
    data class Created(val cyclistId: Uuid) : AddTripState()
    data class Error(val type: String, val detail: String) : AddTripState()
}

sealed class AddTripAction : Action {
    data class Create(
        val cyclistId: Uuid,
        val from: Location,
        val to: Location,
        val schedule: LocalTime,
        val duration: Duration,
        val roundTripStart: LocalTime? = null,
        val name: String? = null,
    ) : AddTripAction()

    data class Created(val cyclistId: Uuid) : AddTripAction()
    data class Error(val type: String, val detail: String) : AddTripAction()
}

sealed class AddTripEffect : Effect

class AddTrip(
    private val zigWheeloApi: ZigWheeloApi
) : Store<AddTripState, AddTripAction, AddTripEffect>,
    CoroutineScope by CoroutineScope(Dispatchers.Main) {

    private val state = MutableStateFlow<AddTripState>(AddTripState.Pending)
    private val sideEffect = MutableSharedFlow<AddTripEffect>()

    override fun observeState(): StateFlow<AddTripState> = state

    override fun observeSideEffect(): Flow<AddTripEffect> = sideEffect

    override fun dispatch(action: AddTripAction) {
        Napier.v(tag = "AddTrip", message = "Action: $action")
        val oldState = state.value
        val newState: AddTripState = when (action) {
            is AddTripAction.Create -> {
                launch {
                    addTrip(
                        action.from,
                        action.to,
                        action.schedule,
                        action.duration,
                        action.roundTripStart,
                        action.name
                    )
                }
                AddTripState.Creating
            }
            is AddTripAction.Created -> {
                AddTripState.Created(action.cyclistId)
            }
            is AddTripAction.Error -> {
                AddTripState.Error(action.type, action.detail)
            }
        }
        if (newState != oldState) {
            state.value = newState
        }
    }

    private suspend fun addTrip(
        from: Location,
        to: Location,
        schedule: LocalTime,
        duration: Duration,
        roundTripStart: LocalTime? = null,
        name: String? = null,
    ) {
        try {
            val response = zigWheeloApi.onboard.addTrip(
                AddTripRequest(
                    from,
                    to,
                    schedule,
                    duration,
                    roundTripStart,
                    name
                )
            )

            dispatch(response.fold({
                AddTripAction.Error(it.type, it.message)
            }, {
                AddTripAction.Created(it.cyclistId)
            }))
        } catch (e: Exception) {
            dispatch(
                AddTripAction.Error(
                    "fatal",
                    e.message ?: "unknown"
                )
            )
        }
    }
}