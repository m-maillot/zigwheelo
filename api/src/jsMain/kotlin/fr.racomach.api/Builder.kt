package fr.racomach.api

import fr.racomach.api.storage.AppJsDatabase

fun ZigWheeloDependencies.Companion.create(baseUrl: String, withLog: Boolean) =
    ZigWheeloDependenciesBuilder().build(
        baseUrl,
        AppJsDatabase(),
        withLog
    )
