package fr.racomach.api.usecase

import fr.racomach.api.ZigWheeloApi
import fr.racomach.api.generic.Position
import fr.racomach.api.model.Park
import fr.racomach.api.response.FindParkResult
import io.github.aakira.napier.Napier
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed class SearchParkState : State {
    object Loading : SearchParkState()
    data class Loaded(val parks: List<Park>) : SearchParkState()
    data class Error(val detail: String) : SearchParkState()
}

sealed class SearchParkAction : Action {
    data class Search(val latitude: Double, val longitude: Double, val distance: Int) :
        SearchParkAction()

    data class Data(val parks: List<Park>) : SearchParkAction()
    data class Error(val error: Exception) : SearchParkAction()
}

sealed class SearchParkEffect : Effect {
    data class Error(val error: Exception) : SearchParkEffect()
}

class SearchParks(
    private val zigWheeloApi: ZigWheeloApi
) : Store<SearchParkState, SearchParkAction, SearchParkEffect>,
    CoroutineScope by CoroutineScope(Dispatchers.Main) {

    private val state = MutableStateFlow<SearchParkState>(SearchParkState.Loading)
    private val sideEffect = MutableSharedFlow<SearchParkEffect>()

    override fun observeState(): StateFlow<SearchParkState> = state

    override fun observeSideEffect(): Flow<SearchParkEffect> = sideEffect

    override fun dispatch(action: SearchParkAction) {
        Napier.v(tag = "SearchParks", message = "Action: $action")
        val oldState = state.value
        val newState: SearchParkState = when (action) {
            is SearchParkAction.Search -> {
                launch { searchParks(action.latitude, action.longitude, action.distance) }
                SearchParkState.Loading
            }
            is SearchParkAction.Data -> {
                SearchParkState.Loaded(action.parks)
            }
            is SearchParkAction.Error -> {
                SearchParkState.Error(action.error.message ?: "Unknown error")
            }
        }
        if (newState != oldState) {
            state.value = newState
        }
    }

    private suspend fun searchParks(latitude: Double, longitude: Double, distance: Int) {
        try {
            val allParks = zigWheeloApi.searchParks(latitude, longitude, distance)
            dispatch(SearchParkAction.Data(allParks.park.map { it.toPark() }))
        } catch (e: Exception) {
            dispatch(SearchParkAction.Error(e))
        }
    }

    private fun FindParkResult.toPark() = Park(
        id = id,
        address = address,
        spots = spots,
        location = Position(
            latitude = location.latitude,
            longitude = location.longitude
        )
    )
}