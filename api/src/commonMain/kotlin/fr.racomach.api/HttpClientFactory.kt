package fr.racomach.api

import fr.racomach.api.storage.Database
import io.ktor.client.*

internal expect class HttpClientFactory() {
    fun create(database: Database, withLog: Boolean = false): HttpClient
}