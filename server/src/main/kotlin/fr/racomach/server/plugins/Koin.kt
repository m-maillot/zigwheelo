package fr.racomach.server.plugins

import fr.racomach.event.sourcing.*
import fr.racomach.server.repository.UserRepository
import fr.racomach.server.repository.UserRepositoryInMemory
import fr.racomach.server.service.NotificationService
import fr.racomach.server.service.RegisteredUsers
import fr.racomach.server.util.createHttpClient
import io.ktor.server.application.*
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import org.koin.ktor.plugin.Koin
import org.koin.logger.slf4jLogger

fun Application.configureKoin() {

    install(Koin) {
        slf4jLogger()
        modules(module {
            single { createHttpClient() }
            single<UserRepository> { UserRepositoryInMemory() }
            single<EventStore> { EventStoreInMemory() }
            single { selector }
            single<EventPublisher> {
                EventPublisherImpl(
                    get(),
                    listOf(RegisteredUsers(get()), NotificationService())
                )
            }
            singleOf(::CommandHandler)
            querySelector()
            single<QueryPublisher> { QueryPublisherImpl(get()) }

        })
    }

}