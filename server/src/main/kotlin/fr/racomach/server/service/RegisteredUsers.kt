package fr.racomach.server.service

import fr.racomach.event.sourcing.AggregateId
import fr.racomach.event.sourcing.EventHandler
import fr.racomach.event.sourcing.event.CyclistEvent
import fr.racomach.server.repository.UserEntity
import fr.racomach.server.repository.UserRepository

class RegisteredUsers(private val userRepository: UserRepository) : EventHandler<CyclistEvent.Created> {

    override fun on(id: AggregateId, event: CyclistEvent.Created) {
        userRepository.save(UserEntity(id, event.username))
    }

    override fun supportedEvents() = listOf(CyclistEvent.Created::class.qualifiedName!!)
}