package fr.racomach.api.storage

import com.benasher44.uuid.Uuid
import com.benasher44.uuid.uuidFrom
import com.russhwolf.settings.Settings
import com.russhwolf.settings.set
import fr.racomach.api.onboard.model.Step

class AppSettings(private val settings: Settings) {

    fun getOnboardStep(): Step {
        return settings.getString("onboard-step", Step.WELCOME.toString()).let { Step.valueOf(it) }
    }

    fun updateOnboardStep(step: Step) {
        settings["onboard-step"] = step.toString()
    }

    fun saveUserId(userId: Uuid) {
        settings["user-id"] = userId.toString()
    }

    fun getUserId(): Uuid? =
        settings.getStringOrNull("user-id")?.let { uuidFrom(it) }
}