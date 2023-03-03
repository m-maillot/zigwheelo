package fr.racomach.api

import fr.racomach.api.storage.AppSettings
import io.github.aakira.napier.Napier
import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.request.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json

internal actual class HttpClientFactory {
    actual fun create(settings: AppSettings, withLog: Boolean): HttpClient {
        return HttpClient(Android) {
            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                    prettyPrint = true
                    isLenient = true
                })
            }
            defaultRequest {
                settings.getUserId()?.let {
                    header("Authorization", "ZigWheelo $it")
                }
            }
            if (withLog) install(Logging) {
                level = LogLevel.HEADERS
                logger = object : Logger {
                    override fun log(message: String) {
                        Napier.v(tag = "AndroidHttpClient", message = message)
                    }
                }
            }
        }
    }
}