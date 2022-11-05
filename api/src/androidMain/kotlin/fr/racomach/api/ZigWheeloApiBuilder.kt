package fr.racomach.api

import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier

fun createApi(baseUrl: String, withLog: Boolean) = ZigWheeloApi(
    AndroidHttpClient(withLog),
    baseUrl,
).also {
    if (withLog) Napier.base(DebugAntilog())
}