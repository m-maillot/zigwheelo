package fr.racomach.event.sourcing

import arrow.core.Either
import arrow.core.flatMap
import fr.racomach.event.sourcing.command.Command
import fr.racomach.server.error.DomainError
import java.util.logging.Logger

class CommandHandler(
    private val eventStore: EventStore,
    private val aggregateSelector: AggregateSelector,
    private val eventPublisher: EventPublisher,
) {

    fun apply(command: Command): Either<Error, AggregateId> =
        loadEvents(command)
            .flatMap { callAggregatorAndDispatchEvents(command, it) }
            .map { command.id }

    private fun callAggregatorAndDispatchEvents(command: Command, history: EventHistory): Either<Error, Unit> =
        aggregateSelector.select(command).on(history.events, command)
            .also { Logger.getGlobal().info("Command $command received, generate events: $it") }
            .map { newEvents -> eventPublisher.publish(command.id, newEvents, history.version) }

    private fun loadEvents(command: Command): Either<Error, EventHistory> =
        if (command.createCommand) Either.Right(EventHistory(emptyList(), 0))
        else eventStore.get(command.id).takeIf { it.events.isNotEmpty() }?.let { Either.Right(it) }
            ?: Either.Left(DomainError.Generic.NotFound)
}