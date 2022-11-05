package fr.racomach.event.sourcing

import arrow.core.Either
import fr.racomach.event.sourcing.command.TestCommand
import fr.racomach.event.sourcing.event.TestEvent

class AggregateTest : Aggregate<TestEvent, TestCommand> {

    override fun apply(history: List<TestEvent>, command: TestCommand): Either<Exception, List<TestEvent>> =
        when (command) {
            is TestCommand.ChangeAddress -> Either.Right(listOf(TestEvent.AddressChanged(command.address)))
            is TestCommand.Create -> Either.Right(listOf(TestEvent.Created(command.name)))
        }
}