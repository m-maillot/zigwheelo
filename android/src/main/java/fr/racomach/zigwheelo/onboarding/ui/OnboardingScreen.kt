package fr.racomach.zigwheelo.onboarding.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import fr.racomach.zigwheelo.onboarding.ui.component.MainScreen

@Composable
fun OnboardingScreen(
    modifier: Modifier = Modifier,
    viewModel: OnboardingViewModel,
) {
    val state = viewModel.state.collectAsState()
    MainScreen(
        modifier = modifier,
        state = state.value,
    )
}