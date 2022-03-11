package fr.racomach.zigwheelo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import fr.racomach.zigwheelo.parks.model.Park
import fr.racomach.zigwheelo.parks.repository.ParkNetwork
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    val parkRepo = ParkNetwork()

    private val _uiState = MutableStateFlow<State>(State.Loading)
    val uiState: StateFlow<State> = _uiState

    fun load() = viewModelScope.launch {
        val result = parkRepo.searchParks(45.742989978188945, 4.851021720981201, 500)
        _uiState.value = result.fold(
            { State.Error(it.localizedMessage ?: "Inconnue...") },
            { State.Loaded(it) },
        )
    }

    sealed class State {
        object Loading : State()
        data class Loaded(val parks: List<Park>) : State()
        data class Error(val detail: String) : State()
    }
}