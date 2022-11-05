package fr.racomach.server.plugins

import fr.racomach.event.sourcing.*
import fr.racomach.server.repository.UserRepository
import fr.racomach.server.repository.UserRepositoryInMemory
import fr.racomach.server.service.RegisteredUsers
import io.ktor.server.application.*
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import org.koin.ktor.plugin.Koin
import org.koin.logger.slf4jLogger

fun Application.configureKoin() {

    install(Koin) {
        slf4jLogger()
        modules(module {
            single<UserRepository> { UserRepositoryInMemory() }
            single<EventStore> { EventStoreInMemory() }
            single { selector }
            single<EventPublisher> { EventPublisherImpl(get(), listOf(RegisteredUsers(get()))) }
            singleOf(::CommandHandler)
        })
    }

}