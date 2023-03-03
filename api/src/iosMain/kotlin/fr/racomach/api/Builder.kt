@file:Suppress("unused")

package fr.racomach.api

import com.russhwolf.settings.NSUserDefaultsSettings
import com.squareup.sqldelight.drivers.native.NativeSqliteDriver
import fr.racomach.api.storage.AppDatabase
import fr.racomach.api.storage.AppSettings
import fr.racomach.zigwheelo.ZigWheeloDatabase
import platform.Foundation.NSUserDefaults

fun ZigWheeloDependencies.Companion.create(baseUrl: String, withLog: Boolean) =
    ZigWheeloDependenciesBuilder().build(
        baseUrl,
        AppSettings(NSUserDefaultsSettings(NSUserDefaults())),
        AppDatabase(NativeSqliteDriver(ZigWheeloDatabase.Schema, "zigwheelo.db")),
        withLog
    )
