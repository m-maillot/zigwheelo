package fr.racomach.server.feature.locationCondition

import fr.racomach.event.sourcing.CommandHandler
import fr.racomach.event.sourcing.command.LocationConditionCommand
import fr.racomach.server.util.toRespond
import fr.racomach.values.Location
import io.ktor.server.application.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable

fun Route.locationCondition(
    commandHandler: CommandHandler,
) {
    post<CreateLocationRequest>("/location") { input ->
        commandHandler.apply(LocationConditionCommand.NewLocation(input.location))
            .map { CreateLocationResponse(it.toString()) }
            .toRespond(call)
    }
}

@Serializable
data class CreateLocationRequest(val location: Location)

@Serializable
data class CreateLocationResponse(val id: String)
