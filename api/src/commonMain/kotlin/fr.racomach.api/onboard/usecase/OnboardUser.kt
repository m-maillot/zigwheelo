package fr.racomach.api.onboard.usecase

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.benasher44.uuid.Uuid
import fr.racomach.api.ZigWheeloApi
import fr.racomach.api.error.ErrorResponse
import fr.racomach.api.onboard.api.dto.CreateRequest
import fr.racomach.api.usecase.Action
import fr.racomach.api.usecase.Effect
import fr.racomach.api.usecase.State
import fr.racomach.api.usecase.Store
import io.github.aakira.napier.Napier
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

enum class Step {
    WELCOME, TRIP, SETTINGS
}

data class WelcomeStepState(
    val loading: Boolean = false,
    val error: ErrorResponse? = null,
    val id: Uuid? = null,
)

data class TripStepState(
    val loading: Boolean = false,
    val error: ErrorResponse? = null,
    val id: Uuid? = null,
)

data class SettingStepState(
    val loading: Boolean = false,
    val error: ErrorResponse? = null,
    val id: Uuid? = null,
)

data class OnboardingState(
    val welcomeStep: WelcomeStepState? = null,
    val tripStep: TripStepState? = null,
    val settingStep: SettingStepState? = null,
) : State {
    fun currentStep() =
        if (welcomeStep != null)
            Step.WELCOME
        else if (tripStep != null)
            Step.TRIP
        else if (settingStep != null)
            Step.SETTINGS
        else
            throw IllegalStateException("Missing data")
}

sealed class OnboardingAction : Action {
    data class CreateUser(val username: String?) : OnboardingAction()
    data class CreateTrip(val name: String?) : OnboardingAction()
    object SkipTrip : OnboardingAction()
}

sealed class OnboardingEffect : Effect {
    data class Error(val type: String, val detail: String) : OnboardingEffect()
}

class OnboardUser(
    private val zigWheeloApi: ZigWheeloApi
) : Store<OnboardingState, OnboardingAction, OnboardingEffect>,
    CoroutineScope by CoroutineScope(Dispatchers.Main) {

    private val state =
        MutableStateFlow(OnboardingState(welcomeStep = WelcomeStepState()))
    private val sideEffect = MutableSharedFlow<OnboardingEffect>()

    override fun observeState(): StateFlow<OnboardingState> = state

    override fun observeSideEffect(): Flow<OnboardingEffect> = sideEffect

    override fun dispatch(action: OnboardingAction) {
        Napier.v(tag = "OnboardUser", message = "Action: $action")
        when (action) {
            is OnboardingAction.CreateUser -> {
                launch { createCyclist(action.username) }
            }
            is OnboardingAction.CreateTrip -> {
                launch { createTrip(action.name) }
            }
            OnboardingAction.SkipTrip -> {
                state.value = OnboardingState(settingStep = SettingStepState())
            }
        }
    }

    private fun validateCyclistUsername(username: String?): Either<ErrorResponse, String> =
        if (username != null && username.isNotBlank()) {
            username.right()
        } else {
            ErrorResponse("EMPTY", "Le nom d'utilisateur est obligatoire").left()
        }

    private suspend fun createCyclist(username: String?) {
        if (state.value.currentStep() != Step.WELCOME) {
            throw IllegalStateException("")
        }

        validateCyclistUsername(username).tap { usernameValidated ->
            state.value = OnboardingState(welcomeStep = WelcomeStepState(loading = true))
            zigWheeloApi.onboard.create(CreateRequest(usernameValidated))
                .tap {
                    state.value = OnboardingState(welcomeStep = WelcomeStepState(id = it.cyclistId))
                    delay(1000)
                    state.value = OnboardingState(tripStep = TripStepState())
                }
                .tapLeft {
                    state.value = OnboardingState(welcomeStep = WelcomeStepState(error = it))
                }
        }.tapLeft {
            state.value = OnboardingState(welcomeStep = WelcomeStepState(error = it))
        }
    }

    private fun createTrip(name: String?) {
        state.value = OnboardingState(tripStep = TripStepState(loading = true))
    }
}