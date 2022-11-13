package fr.racomach.zigwheelo.onboarding.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import fr.racomach.zigwheelo.onboarding.ui.OnboardingViewModel
import fr.racomach.zigwheelo.ui.theme.ZigwheeloTheme3

@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    state: OnboardingViewModel.State,
) {
    Surface(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.secondary)
            .padding(16.dp),
        shadowElevation = 8.dp,
    ) {
        when (state) {
            OnboardingViewModel.State.Loading -> {
                /* do nothing */
            }
            is OnboardingViewModel.State.Username -> UsernameStep(
                modifier = Modifier.padding(16.dp),
                state = state,
            )
            is OnboardingViewModel.State.Trip -> TripStep(
                modifier = Modifier.padding(16.dp),
                state = state,
            )
            is OnboardingViewModel.State.Notification -> SettingsStep(
                modifier = Modifier.padding(16.dp)
            ) {
                state.onDone()
            }
        }
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true,
    device = Devices.NEXUS_5X,
)
@Composable
private fun MainScreenPreview() {
    ZigwheeloTheme3(darkTheme = false) {
        MainScreen(state = OnboardingViewModel.State.Username.Pending({}))
    }

}

@Preview(
    showBackground = true,
    showSystemUi = true,
    device = Devices.NEXUS_5X,
)
@Composable
private fun MainScreenDarkPreview() {
    ZigwheeloTheme3(darkTheme = true) {
        MainScreen(state = OnboardingViewModel.State.Username.Pending({}))
    }

}