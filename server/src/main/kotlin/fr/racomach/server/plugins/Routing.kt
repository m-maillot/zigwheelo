package fr.racomach.server.plugins

import fr.racomach.event.sourcing.CommandHandler
import fr.racomach.event.sourcing.QueryPublisher
import fr.racomach.server.feature.cyclist.cyclist
import fr.racomach.server.feature.locationCondition.locationCondition
import fr.racomach.server.feature.place.place
import fr.racomach.server.repository.UserRepository
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Application.configureRouting() {

    /*
    val database = DatabaseFactory(
        HikariConfig().ajmPostgresConfig(
            System.getenv("JDBC_DATABASE_URL"),
            System.getenv("JDBC_DATABASE_USER"),
            System.getenv("JDBC_DATABASE_PASSWORD"),
        )
    )*/

    // val repository = ParkDatabase(database)
    val queryPublisher by inject<QueryPublisher>()
    val commandHandler by inject<CommandHandler>()
    val userRepository by inject<UserRepository>()

    routing {
        get("/") {
            call.respondText("Hello World!")
        }
    }

    routing {
        route("/api/v1") {
            // findParkAroundPosition(FindParkAroundPosition(repository))
            locationCondition(commandHandler)
            cyclist(commandHandler, userRepository)
            place(queryPublisher)
        }
    }
}
