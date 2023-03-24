package fr.racomach.zigwheelo.onboarding.ui.component.trip

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import com.google.android.gms.maps.model.LatLng
import fr.racomach.zigwheelo.R
import fr.racomach.zigwheelo.common.component.FixedMarker
import fr.racomach.zigwheelo.common.component.MarkerOptions
import fr.racomach.zigwheelo.common.component.SelectPosition
import kotlinx.datetime.LocalTime

@Composable
fun NewTrip(
    modifier: Modifier = Modifier,
    onSubmit: (trip: Trip) -> Unit,
) {
    val state = remember { mutableStateOf<LocalState>(LocalState.DepartStep) }

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween,
    ) {
        when (val currentState = state.value) {
            LocalState.DepartStep -> {
                SelectPosition(
                    title = "Point de départ",
                    defaultPosition = LatLng(45.764043, 4.835659),
                    markerOptions = MarkerOptions(icon = R.drawable.map_pin_start),
                    validationText = "Valider la position"
                ) { state.value = LocalState.FinishStep(start = it) }
            }
            is LocalState.DoneStep -> {
                onSubmit(
                    Trip(
                        currentState.start,
                        currentState.finish,
                        currentState.startAt,
                        currentState.durationMinutes,
                        currentState.roundTripAt,
                    )
                )
            }
            is LocalState.FinishStep -> {
                SelectPosition(
                    title = "Point d'arrivée",
                    defaultPosition = currentState.start,
                    markerOptions = MarkerOptions(icon = R.drawable.map_pin_finish),
                    otherMarkers = listOf(
                        FixedMarker(
                            position = currentState.start,
                            options = MarkerOptions(
                                title = "Point de départ",
                                icon = R.drawable.map_pin_start,
                            ),
                        )
                    ),
                    validationText = "Valider la position"
                ) { state.value = LocalState.TimeStep(start = currentState.start, finish = it) }
            }
            is LocalState.TimeStep -> {
                SelectTimes(onSubmit = { startAt, durationInMinutes, roundTripAt ->
                    state.value = LocalState.DoneStep(
                        start = currentState.start,
                        finish = currentState.finish,
                        startAt = startAt,
                        durationInMinutes,
                        roundTripAt
                    )
                })
            }
        }
    }
}

sealed class LocalState {
    object DepartStep : LocalState()
    data class FinishStep(val start: LatLng) : LocalState()
    data class TimeStep(val start: LatLng, val finish: LatLng) : LocalState()
    data class DoneStep(
        val start: LatLng,
        val finish: LatLng,
        val startAt: LocalTime,
        val durationMinutes: Int,
        val roundTripAt: LocalTime,
    ) : LocalState()
}

@Preview(
    showBackground = true,
    device = Devices.NEXUS_6,
)
@Composable
private fun NewTripPreview() {
    NewTrip {}
}