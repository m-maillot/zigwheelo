package fr.racomach.api.storage

import com.benasher44.uuid.Uuid
import com.squareup.sqldelight.EnumColumnAdapter
import com.squareup.sqldelight.db.SqlDriver
import fr.racomach.api.onboard.model.Step
import fr.racomach.zigwheelo.ZigWheeloDatabase
import frracomachzigwheelo.Settings

internal interface Database {
    fun loadSettings(): Settings
    fun setupUser(userId: Uuid)
    fun updateOnboardStep(current: Step)
}

internal class AppDatabase(driver: SqlDriver) : Database {
    private val database = ZigWheeloDatabase(
        driver = driver,
        SettingsAdapter = Settings.Adapter(onboardStepAdapter = EnumColumnAdapter())
    )
    private val dbQuery = database.zigWheeloDatabaseQueries

    init {
        if (dbQuery.selectSettings().executeAsOneOrNull() == null) {
            dbQuery.insertSettings(null, Step.WELCOME)
        }
    }

    override fun loadSettings(): Settings = dbQuery.selectSettings().executeAsOne()

    override fun setupUser(userId: Uuid) {
        if (loadSettings().userId != null) {
            throw IllegalStateException("User is already set")
        }
        dbQuery.updateUser(userId.toString())
    }

    override fun updateOnboardStep(current: Step) {
        dbQuery.updateOnboardStep(current)
    }
}