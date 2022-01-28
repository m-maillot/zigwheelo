package fr.racomach

import io.ktor.server.engine.*
import io.ktor.server.cio.*
import fr.racomach.plugins.*

fun main() {
    embeddedServer(CIO, port = 8080, host = "0.0.0.0") {
        configureRouting()
        configureSerialization()
        configureMonitoring()
    }.start(wait = true)
}
