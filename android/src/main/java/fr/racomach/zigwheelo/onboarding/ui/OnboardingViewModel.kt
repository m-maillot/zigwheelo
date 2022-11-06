package fr.racomach.zigwheelo.onboarding.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import fr.racomach.api.createApi
import fr.racomach.api.cyclist.dto.CreateRequest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class OnboardingViewModel : ViewModel() {

    private val api = createApi("http://", true)
    private val _state = MutableStateFlow<State>(State.Username())
    val state: StateFlow<State>
        get() = _state

    fun onSubmitUsername(username: String) = viewModelScope.launch {
        _state.value = api.cyclist.create(CreateRequest(username))
            .fold(
                ifLeft = { State.Username(it.message) },
                ifRight = { State.Trip() }
            )
    }

    sealed class State {
        data class Username(val error: String? = null) : State()
        data class Trip(val error: String? = null) : State()
    }
}