package fr.racomach.server

import com.google.firebase.FirebaseApp
import fr.racomach.server.plugins.*
import io.ktor.server.cio.*
import io.ktor.server.engine.*

fun main() {
    runCatching {
        FirebaseApp.initializeApp()
        println("Firebase app initialized")
    }
    embeddedServer(CIO, port = 9580, host = "0.0.0.0", watchPaths = listOf("classes")) {
        configureKoin()
        configureAuthentication()
        configureCors()
        configureRouting()
        configureSerialization()
        configureMonitoring()
        statusPage()
    }.start(wait = true)
}
