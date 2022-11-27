import androidx.compose.runtime.collectAsState
import fr.racomach.api.ZigWheeloDependencies
import fr.racomach.api.create
import fr.racomach.api.onboard.model.Step
import fr.racomach.api.onboard.usecase.OnboardUser
import fr.racomach.api.onboard.usecase.OnboardingAction
import org.jetbrains.compose.web.dom.Text
import org.jetbrains.compose.web.renderComposable

fun main() {
    val dependencies = ZigWheeloDependencies.Companion.create("http://localhost:9580", true)
    val onboardUser = OnboardUser(dependencies)
    renderComposable(rootElementId = "root") {
        val state = onboardUser.observeState().collectAsState()

        when (state.value.currentStep()) {
            Step.WELCOME -> RegisterForm(state.value.welcomeStep!!) {
                onboardUser.dispatch(OnboardingAction.CreateUser(it))
            }
            Step.TRIP -> Text("Trip")
            Step.SETTINGS -> Text("Settings")
            Step.DONE -> Text("Onboard done !")
        }
    }
}
