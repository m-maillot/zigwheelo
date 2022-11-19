package fr.racomach.server.plugins

import fr.racomach.event.sourcing.*
import fr.racomach.server.repository.UserRepository
import fr.racomach.server.repository.UserRepositoryInMemory
import fr.racomach.server.service.RegisteredUsers
import fr.racomach.server.util.createHttpClient
import io.github.aakira.napier.Napier
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import kotlinx.serialization.json.Json
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
            single<EventPublisher> { EventPublisherImpl(get(), listOf(RegisteredUsers(get()))) }
            singleOf(::CommandHandler)
            querySelector()
            single<QueryPublisher> { QueryPublisherImpl(get()) }
        })
    }

}