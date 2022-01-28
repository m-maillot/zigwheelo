package fr.racomach.server.plugins

import fr.racomach.database.connectDabatase
import fr.racomach.database.park.ParkDatabase
import fr.racomach.server.feature.parkAroundPosition.FindParkAroundPosition
import fr.racomach.server.feature.parkAroundPosition.findParkAroundPosition
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting() {

    val database = connectDabatase(
        System.getenv("MONGODB_URI"),
        System.getenv("MONGODB_DATABASE"),
    )

    val repository = ParkDatabase(database)

    routing {
        get("/") {
            call.respondText("Hello World!")
        }
    }

    findParkAroundPosition(FindParkAroundPosition(repository))
}
