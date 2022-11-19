package fr.racomach.event.sourcing

import arrow.core.Either
import fr.racomach.event.sourcing.command.Command
import fr.racomach.event.sourcing.event.Event

interface Aggregate<E : Event, C : Command> {

    @Suppress("UNCHECKED_CAST")
    fun on(history: List<Event>, command: Command): Either<Error, List<Event>> {
        return apply(history as List<E>, command as C)
    }

    fun apply(history: List<E>, command: C): Either<Error, List<E>>
}