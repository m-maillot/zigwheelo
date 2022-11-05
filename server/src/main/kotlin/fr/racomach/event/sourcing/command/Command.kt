package fr.racomach.event.sourcing.command

import fr.racomach.event.sourcing.AggregateId

sealed interface Command {
    val id: AggregateId
    val createCommand: Boolean
}