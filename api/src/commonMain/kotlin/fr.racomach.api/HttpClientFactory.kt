package fr.racomach.api

import fr.racomach.api.storage.AppSettings
import io.ktor.client.*

internal expect class HttpClientFactory() {
    fun create(settings: AppSettings, withLog: Boolean = false): HttpClient
}