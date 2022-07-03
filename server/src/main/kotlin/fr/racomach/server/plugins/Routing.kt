package fr.racomach.server.plugins

import com.zaxxer.hikari.HikariConfig
import fr.racomach.database.DatabaseFactory
import fr.racomach.database.ajmPostgresConfig
import fr.racomach.database.park.ParkDatabase
import fr.racomach.server.feature.parkAroundPosition.FindParkAroundPosition
import fr.racomach.server.feature.parkAroundPosition.findParkAroundPosition
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting() {

    val database = DatabaseFactory(
        HikariConfig().ajmPostgresConfig(
            System.getenv("JDBC_DATABASE_URL"),
            System.getenv("JDBC_DATABASE_USER"),
            System.getenv("JDBC_DATABASE_PASSWORD"),
        )
    )

    val repository = ParkDatabase(database)

    routing {
        get("/") {
            call.respondText("Hello World!")
        }
    }

    findParkAroundPosition(FindParkAroundPosition(repository))
}
