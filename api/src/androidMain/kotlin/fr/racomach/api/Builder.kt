package fr.racomach.api

import android.content.Context
import com.russhwolf.settings.SharedPreferencesSettings
import com.squareup.sqldelight.android.AndroidSqliteDriver
import fr.racomach.api.storage.AppDatabase
import fr.racomach.api.storage.AppSettings
import fr.racomach.zigwheelo.ZigWheeloDatabase

fun ZigWheeloDependencies.Companion.create(context: Context, baseUrl: String, withLog: Boolean) =
    ZigWheeloDependenciesBuilder().build(
        baseUrl,
        AppSettings(
            SharedPreferencesSettings(
                context.getSharedPreferences(
                    "settings",
                    Context.MODE_PRIVATE
                )
            )
        ),
        AppDatabase(
            AndroidSqliteDriver(
                ZigWheeloDatabase.Schema,
                context,
                "zigwheelo.db"
            )
        ),
        withLog
    )