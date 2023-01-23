package fr.racomach.zigwheelo.onboarding.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import fr.racomach.api.onboard.model.Step
import fr.racomach.api.onboard.usecase.OnboardingAction
import fr.racomach.api.onboard.usecase.OnboardingState
import fr.racomach.api.onboard.usecase.WelcomeStepState
import fr.racomach.zigwheelo.ui.theme.ZigwheeloTheme3

@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    state: OnboardingState,
    dispatch: (OnboardingAction) -> Unit,
) {
    Surface(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.secondary)
            .padding(16.dp),
        shadowElevation = 8.dp,
    ) {
        when (state.currentStep()) {
            Step.WELCOME -> UsernameStep(
                modifier = Modifier.padding(16.dp),
                state = state.welcomeStep!!,
            ) {
                dispatch(OnboardingAction.CreateUser(it))
            }
            Step.TRIP -> TripStep(
                modifier = Modifier.padding(16.dp),
                onSkip = { dispatch(OnboardingAction.SkipTrip) }
            )
            Step.SETTINGS -> SettingsStep(
                modifier = Modifier.padding(16.dp),
                state = state.settingStep!!,
                onAcceptNotification = {
                    dispatch(
                        OnboardingAction.UpdateSettings(
                            token = "",
                            notificationAt = it
                        )
                    )
                },
                onDenyNotification = { dispatch(OnboardingAction.UpdateSettings(acceptNotification = false)) }
            )
            Step.DONE -> {
                Text(text = "Hello world !")
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
        MainScreen(
            state = OnboardingState(welcomeStep = WelcomeStepState()),
            dispatch = {},
        )
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
        MainScreen(
            state = OnboardingState(welcomeStep = WelcomeStepState()),
            dispatch = {},
        )
    }

}