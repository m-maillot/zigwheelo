package fr.racomach.server.plugins

import io.ktor.server.application.*
import io.ktor.server.plugins.*


fun Application.configureCors() {

    install(CORS) {
        anyHost()
    }

}