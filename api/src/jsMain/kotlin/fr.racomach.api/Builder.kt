package fr.racomach.api

import com.russhwolf.settings.StorageSettings
import fr.racomach.api.storage.AppJsDatabase
import fr.racomach.api.storage.AppSettings

fun ZigWheeloDependencies.Companion.create(baseUrl: String, withLog: Boolean) =
    ZigWheeloDependenciesBuilder().build(
        baseUrl,
        AppSettings(StorageSettings()),
        AppJsDatabase(),
        withLog
    )
