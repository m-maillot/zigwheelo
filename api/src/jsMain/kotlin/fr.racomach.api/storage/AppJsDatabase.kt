package fr.racomach.api.storage

import com.benasher44.uuid.Uuid
import fr.racomach.api.onboard.model.Step
import frracomachzigwheelo.Settings
import kotlinx.browser.window
import org.w3c.dom.get

internal class AppJsDatabase : Database {
    override fun loadSettings(): Settings =
        Settings(
            userId = window.localStorage["cyclistID"],
            onboardStep = window.localStorage["onboardStep"]?.let { Step.valueOf(it) }
                ?: Step.WELCOME
        )

    override fun setupUser(userId: Uuid) {
        window.localStorage.setItem("cyclistID", userId.toString())
    }

    override fun updateOnboardStep(current: Step) {
        window.localStorage.setItem("onboardStep", current.toString())
    }
}