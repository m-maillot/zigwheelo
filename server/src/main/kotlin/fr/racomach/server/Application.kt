package fr.racomach.server

import fr.racomach.server.plugins.*
import io.ktor.server.cio.*
import io.ktor.server.engine.*

fun main() {
    embeddedServer(CIO, port = 9580, host = "0.0.0.0") {
        configureKoin()
        configureAuthentication()
        configureCors()
        configureRouting()
        configureSerialization()
        configureMonitoring()
    }.start(wait = true)
}
