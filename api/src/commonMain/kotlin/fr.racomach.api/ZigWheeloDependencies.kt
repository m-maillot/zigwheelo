package fr.racomach.api

import fr.racomach.api.storage.AppSettings
import fr.racomach.api.storage.Database
import io.ktor.client.*

internal class ZigWheeloDependenciesBuilder {
    fun build(
        baseUrl: String,
        settings: AppSettings,
        database: Database,
        withLog: Boolean = false
    ) =
        ZigWheeloDependencies(
            baseUrl = baseUrl,
            httpClient = HttpClientFactory().create(settings, withLog),
            settings = settings,
            database = database,
        )
}

class ZigWheeloDependencies internal constructor(
    baseUrl: String,
    httpClient: HttpClient,
    internal val settings: AppSettings,
    internal val database: Database,
    internal val api: ZigWheeloApi = ZigWheeloApi(httpClient, baseUrl)
) {
    companion object
}