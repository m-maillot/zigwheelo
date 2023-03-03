package fr.racomach.api.onboard.usecase

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.benasher44.uuid.Uuid
import fr.racomach.api.ZigWheeloDependencies
import fr.racomach.api.error.ErrorResponse
import fr.racomach.api.onboard.api.dto.CreateRequest
import fr.racomach.api.onboard.api.dto.SetupNotificationRequest
import fr.racomach.api.onboard.model.Step
import fr.racomach.api.usecase.Action
import fr.racomach.api.usecase.Effect
import fr.racomach.api.usecase.State
import fr.racomach.api.usecase.Store
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalTime

data class WelcomeStepState(
    val loading: Boolean = false,
    val error: ErrorResponse? = null,
    val username: String? = null,
)

data class TripStepState(
    val loading: Boolean = false,
    val error: ErrorResponse? = null,
    val id: Uuid? = null,
)

data class SettingStepState(
    val loading: Boolean = false,
    val error: ErrorResponse? = null,
)

data class OnboardingState(
    val welcomeStep: WelcomeStepState? = null,
    val tripStep: TripStepState? = null,
    val settingStep: SettingStepState? = null,
    val done: Boolean = false,
) : State {
    fun currentStep() =
        if (welcomeStep != null)
            Step.WELCOME
        else if (tripStep != null)
            Step.TRIP
        else if (settingStep != null)
            Step.SETTINGS
        else if (done)
            Step.DONE
        else
            throw IllegalStateException("Missing data")
}

sealed class OnboardingAction : Action {
    data class CreateUser(val username: String?) : OnboardingAction()
    data class CreateTrip(val name: String?) : OnboardingAction()
    object SkipTrip : OnboardingAction()
    data class UpdateSettings(
        val acceptNotification: Boolean = true,
        val token: String? = null,
        val notificationAt: LocalTime? = null,
    ) :
        OnboardingAction()

    object SkipSettings : OnboardingAction()
}

sealed class OnboardingEffect : Effect {
    data class Error(val type: String, val detail: String) : OnboardingEffect()
}

class OnboardUser(
    dependencies: ZigWheeloDependencies,
) : Store<OnboardingState, OnboardingAction, OnboardingEffect>,
    CoroutineScope by CoroutineScope(Dispatchers.Main) {

    private val api = dependencies.api
    private val settings = dependencies.settings

    private val state =
        MutableStateFlow(OnboardingState(welcomeStep = WelcomeStepState()))
    private val sideEffect = MutableSharedFlow<OnboardingEffect>()

    override fun observeState(): StateFlow<OnboardingState> = state

    override fun observeSideEffect(): Flow<OnboardingEffect> = sideEffect

    init {
        state.value = when (settings.getOnboardStep()) {
            Step.WELCOME -> OnboardingState(welcomeStep = WelcomeStepState())
            Step.TRIP -> OnboardingState(tripStep = TripStepState())
            Step.SETTINGS -> OnboardingState(settingStep = SettingStepState())
            Step.DONE -> OnboardingState(done = true)
        }
    }

    override fun dispatch(action: OnboardingAction) {
        when (action) {
            is OnboardingAction.CreateUser -> {
                launch { createCyclist(action.username) }
            }
            is OnboardingAction.CreateTrip -> {
                launch { createTrip(action.name) }
            }
            OnboardingAction.SkipTrip -> {
                settings.updateOnboardStep(Step.SETTINGS)
                state.value = OnboardingState(settingStep = SettingStepState())
            }
            is OnboardingAction.UpdateSettings -> {
                launch { setupNotification(action.token, action.notificationAt) }
            }
            OnboardingAction.SkipSettings -> {
                settings.updateOnboardStep(Step.DONE)
                state.value = OnboardingState(done = true)
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

        validateCyclistUsername(username).onRight { usernameValidated ->
            state.value = OnboardingState(welcomeStep = WelcomeStepState(loading = true))
            api.onboard.create(CreateRequest(usernameValidated))
                .onRight {
                    settings.saveUserId(it.cyclistId)
                    settings.updateOnboardStep(Step.TRIP)
                    state.value =
                        OnboardingState(welcomeStep = WelcomeStepState(username = username))
                    delay(1000)
                    state.value = OnboardingState(tripStep = TripStepState())
                }
                .onLeft {
                    state.value = OnboardingState(welcomeStep = WelcomeStepState(error = it))
                }
        }.onLeft {
            state.value = OnboardingState(welcomeStep = WelcomeStepState(error = it))
        }
    }

    private fun createTrip(name: String?) {
        state.value = OnboardingState(tripStep = TripStepState(loading = true))
    }

    private suspend fun setupNotification(token: String?, notificationAt: LocalTime?) {
        state.value = OnboardingState(settingStep = SettingStepState(loading = true))
        api.onboard.setupNotification(SetupNotificationRequest(token, notificationAt))
            .onRight {
                settings.updateOnboardStep(Step.DONE)
                state.value = OnboardingState(done = true)
            }
            .onLeft {
                state.value = OnboardingState(settingStep = SettingStepState(error = it))
            }
    }
}