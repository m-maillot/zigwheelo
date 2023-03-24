package fr.racomach.zigwheelo.onboarding.ui.component

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import fr.racomach.api.onboard.usecase.TripStepState
import fr.racomach.zigwheelo.onboarding.ui.component.trip.NewTrip
import fr.racomach.zigwheelo.onboarding.ui.component.trip.NoTripFilled
import fr.racomach.zigwheelo.onboarding.ui.component.trip.Trip
import fr.racomach.zigwheelo.onboarding.ui.component.trip.TripFilled
import fr.racomach.zigwheelo.ui.theme.ZigwheeloTheme3

@Composable
fun TripStep(
    modifier: Modifier = Modifier,
    state: TripStepState,
    onSubmit: (trip: Trip) -> Unit,
    onSkip: () -> Unit,
) {
    val internalState = remember { mutableStateOf<TripState>(TripState.None) }


    when (val currentState = internalState.value) {
        TripState.None -> NoTripFilled(
            modifier,
            { internalState.value = TripState.InProgress },
            onSkip
        )
        TripState.InProgress -> NewTrip(onSubmit = { internalState.value = TripState.Done(it) })
        is TripState.Done -> TripFilled(
            trip = currentState.trip,
            isLoading = state.loading,
            error = state.error,
            onSubmit = onSubmit
        )
    }
}

private sealed class TripState {
    object None : TripState()
    object InProgress : TripState()
    data class Done(val trip: Trip) : TripState()
}


@Preview(
    showBackground = true,
    showSystemUi = true,
    device = Devices.NEXUS_5X,
)
@Composable
private fun TripStepPreview() {
    ZigwheeloTheme3 {
        TripStep(state = TripStepState(), onSkip = {}, onSubmit = {})
    }
}