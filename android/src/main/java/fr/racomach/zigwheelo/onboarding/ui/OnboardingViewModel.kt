package fr.racomach.zigwheelo.onboarding.ui

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import fr.racomach.api.onboard.usecase.OnboardUser
import fr.racomach.api.onboard.usecase.OnboardingAction
import fr.racomach.api.onboard.usecase.OnboardingState
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class OnboardingViewModel @Inject constructor(
    private val onboardUser: OnboardUser
) : ViewModel() {

    val state: StateFlow<OnboardingState> = onboardUser.observeState()

    val dispatch: (OnboardingAction) -> Unit = { onboardUser.dispatch(it) }
}