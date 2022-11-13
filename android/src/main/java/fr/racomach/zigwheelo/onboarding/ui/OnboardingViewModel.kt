package fr.racomach.zigwheelo.onboarding.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import fr.racomach.api.createApi
import fr.racomach.zigwheelo.onboarding.usecase.RegisterUsername
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnboardingViewModel @Inject constructor(
    private val registerUsername: RegisterUsername,
) : ViewModel() {

    private val api = createApi("http://", true)
    private val _state = MutableStateFlow<State>(State.Loading)
    val state: StateFlow<State>
        get() = _state

    init {
        start()
    }

    private fun start() = viewModelScope.launch {
        registerUsername.run().collect(::handleRegisterUsername)
    }

    private suspend fun handleRegisterUsername(state: RegisterUsername.State) {
        when (state) {
            is RegisterUsername.State.AskUsername -> _state.value =
                State.Username.Pending({ state.callback.complete(it) }, state.error)
            RegisterUsername.State.Succeed -> {
                _state.value = State.Username.Succeed
                delay(500)
                _state.value = State.Trip(onCreateTrip = {}, onSkip = {})
            }
            RegisterUsername.State.Validating -> _state.value = State.Username.Loading
        }
    }

    sealed class State {
        object Loading : State()

        sealed class Username : State() {
            object Loading : Username()
            data class Pending(val onSubmit: (String?) -> Unit, val error: String? = null) :
                Username()

            object Succeed : Username()
        }

        data class Trip(val onCreateTrip: () -> Unit, val onSkip: () -> Unit) : State()
    }
}