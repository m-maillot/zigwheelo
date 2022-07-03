package fr.racomach.server

import io.ktor.server.engine.*
import io.ktor.server.cio.*
import fr.racomach.server.plugins.configureCors
import fr.racomach.server.plugins.configureMonitoring
import fr.racomach.server.plugins.configureRouting
import fr.racomach.server.plugins.configureSerialization

fun main() {
    embeddedServer(CIO, port = 9580, host = "0.0.0.0") {
        configureCors()
        configureRouting()
        configureSerialization()
        configureMonitoring()
    }.start(wait = true)
}
