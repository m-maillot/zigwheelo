package fr.racomach.zigwheelo.onboarding.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.messaging.FirebaseMessaging
import dagger.hilt.android.lifecycle.HiltViewModel
import fr.racomach.api.onboard.usecase.OnboardUser
import fr.racomach.api.onboard.usecase.OnboardingAction
import fr.racomach.api.onboard.usecase.OnboardingState
import fr.racomach.zigwheelo.notifications.NotificationRepository
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

@HiltViewModel
class OnboardingViewModel @Inject constructor(
    private val onboardUser: OnboardUser,
    private val notificationRepository: NotificationRepository,
) : ViewModel() {

    val state: StateFlow<OnboardingState> = onboardUser.observeState()

    val dispatch: (OnboardingAction) -> Unit = {
        if (it is OnboardingAction.UpdateSettings) {
            notificationRepository.createDailyChannel()
            viewModelScope.launch {
                onboardUser.dispatch(
                    OnboardingAction.UpdateSettings(
                        token = FirebaseMessaging.getInstance().token.await(),
                        notificationAt = it.notificationAt,
                        acceptNotification = true,
                    )
                )
            }
        } else {
            onboardUser.dispatch(it)
        }
    }
}