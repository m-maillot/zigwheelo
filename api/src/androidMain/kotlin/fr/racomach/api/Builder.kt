package fr.racomach.api

import android.content.Context
import com.squareup.sqldelight.android.AndroidSqliteDriver
import fr.racomach.api.storage.AppDatabase
import fr.racomach.zigwheelo.ZigWheeloDatabase

fun ZigWheeloDependencies.Companion.create(context: Context, baseUrl: String, withLog: Boolean) =
    ZigWheeloDependenciesBuilder().build(
        baseUrl,
        AppDatabase(AndroidSqliteDriver(ZigWheeloDatabase.Schema, context, "zigwheelo.db")),
        withLog
    )