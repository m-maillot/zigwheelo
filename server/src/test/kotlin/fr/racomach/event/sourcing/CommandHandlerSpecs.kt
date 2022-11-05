package fr.racomach.event.sourcing

import fr.racomach.event.sourcing.command.TestCommand
import fr.racomach.event.sourcing.event.TestEvent
import fr.racomach.server.error.DomainError
import io.kotest.assertions.arrow.core.shouldBeLeft
import io.kotest.assertions.arrow.core.shouldBeRight
import io.kotest.assertions.withClue
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class CommandHandlerSpecs : FunSpec({
    test("Quand je passe une commande de création, j'attends à appeler le publisher pour stocker les events mais pas le store") {
        val eventPublisher = EventPublisherMock()
        val eventStore = EventStoreStub()

        val commandHandler = CommandHandler(eventStore, testSelector, eventPublisher)
        commandHandler.apply(TestCommand.Create("nom"))

        eventStore.getHasBeenCalled.get() shouldBe 0
        eventPublisher.calls.size shouldBe 1
    }

    test("Quand je passe une commande, j'attends à appeler le store pour récupérer les events puis le publisher") {
        val eventPublisher = EventPublisherMock()
        val eventStore =
            EventStoreStub(getResponse = EventHistory(listOf(TestEvent.Created("nom")), 1))

        val aggregateId = AggregateId()
        val commandHandler = CommandHandler(eventStore, testSelector, eventPublisher)
        val response = commandHandler.apply(TestCommand.ChangeAddress(aggregateId, "new address"))

        response shouldBeRight aggregateId
        withClue("Name should be present") {
            eventStore.getHasBeenCalled.get() shouldBe 1
        }
        eventPublisher.calls.size shouldBe 1
    }

    test("Quand je passe une commande sur un aggregate inexistant, j'attends à recevoir une erreur") {
        val eventPublisher = EventPublisherMock()
        val eventStore = EventStoreStub(getResponse = EventHistory(emptyList(), 0))

        val commandHandler = CommandHandler(eventStore, testSelector, eventPublisher)
        val response = commandHandler.apply(TestCommand.ChangeAddress(AggregateId(), "new address"))

        response shouldBeLeft DomainError.Generic.NotFound
        eventStore.getHasBeenCalled.get() shouldBe 1
        eventPublisher.calls.size shouldBe 0
    }
})