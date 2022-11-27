package fr.racomach.api

import fr.racomach.api.storage.Database
import io.github.aakira.napier.Napier
import io.ktor.client.*
import io.ktor.client.engine.js.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.request.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json

internal actual class HttpClientFactory {
    actual fun create(database: Database, withLog: Boolean): HttpClient {
        return HttpClient(Js) {
            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                    prettyPrint = true
                    isLenient = true
                })
            }

            defaultRequest {
                database.loadSettings().userId?.let {
                    header("Authorization", "ZigWheelo $it")
                }
            }

            if (withLog) install(Logging) {
                level = LogLevel.HEADERS
                logger = object : Logger {
                    override fun log(message: String) {
                        Napier.v(tag = "JsHttpClient", message = message)
                    }
                }
            }
        }
    }
}