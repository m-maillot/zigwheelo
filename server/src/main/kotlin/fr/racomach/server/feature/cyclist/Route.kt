package fr.racomach.server.feature.cyclist

import arrow.core.Either
import fr.racomach.api.onboard.api.dto.AddTripRequest
import fr.racomach.api.onboard.api.dto.CreateRequest
import fr.racomach.api.onboard.api.dto.CreateResponse
import fr.racomach.api.onboard.api.dto.SetupNotificationRequest
import fr.racomach.event.sourcing.AggregateId
import fr.racomach.event.sourcing.CommandHandler
import fr.racomach.event.sourcing.command.CyclistCommand
import fr.racomach.server.error.DomainError
import fr.racomach.server.plugins.AuthenticatedUser
import fr.racomach.server.repository.UserRepository
import fr.racomach.server.util.toRespond
import fr.racomach.server.util.toRespondAccepted
import fr.racomach.values.Trip
import fr.racomach.values.toLocation
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.routing.*

fun Route.cyclist(
    commandHandler: CommandHandler,
    userRepository: UserRepository,
) {
    post<CreateRequest>("/cyclist/create") { input ->
        if (userRepository.exist(input.username)) {
            Either.Left(DomainError.Cyclist.UsernameAlreadyTaken(input.username)).toRespond(call)
        } else {
            commandHandler.apply(CyclistCommand.Create(input.username))
                .map { CreateResponse(it.id) }
                .toRespond(call)
        }
    }
    authenticate("custom") {
        post<AddTripRequest>("/cyclist/trip") { input ->
            val cyclistId = (this.call.authentication.principal as AuthenticatedUser).id
            commandHandler.apply(input.toCommand(cyclistId))
                .map { it.id.toString() }
                .toRespond(call)
        }

        post<SetupNotificationRequest>("/cyclist/notification") { input ->
            val cyclistId = (this.call.authentication.principal as AuthenticatedUser).id
            commandHandler.apply(input.toCommand(cyclistId))
                .map { it.id.toString() }
                .toRespondAccepted(call)
        }
    }
}

private fun AddTripRequest.toCommand(id: AggregateId) = CyclistCommand.AddTrip(
    cyclistId = id,
    trip = Trip(from.toLocation(), to.toLocation(), schedule, duration),
    roundTripStart,
    name,
)

private fun SetupNotificationRequest.toCommand(id: AggregateId) = CyclistCommand.SetupNotification(
    cyclistId = id,
    token,
    notificationAt,
)
