package fr.racomach.api

import com.squareup.sqldelight.drivers.native.NativeSqliteDriver
import fr.racomach.api.storage.AppDatabase
import fr.racomach.zigwheelo.ZigWheeloDatabase

fun ZigWheeloDependencies.Companion.create(baseUrl: String, withLog: Boolean) =
    ZigWheeloDependenciesBuilder().build(
        baseUrl,
        AppDatabase(NativeSqliteDriver(ZigWheeloDatabase.Schema, "zigwheelo.db")),
        withLog
    )
