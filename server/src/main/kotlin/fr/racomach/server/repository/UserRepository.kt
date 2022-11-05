package fr.racomach.server.repository

import fr.racomach.event.sourcing.command.CyclistId

data class UserEntity(
    val id: CyclistId,
    val username: String,
)

interface UserRepository {

    fun save(user: UserEntity)

    fun get(id: CyclistId): UserEntity?

    fun exist(username: String): Boolean
}

class UserRepositoryInMemory : UserRepository {
    private val users: MutableMap<CyclistId, UserEntity> = mutableMapOf()

    override fun save(user: UserEntity) {
        users[user.id] = user
    }

    override fun get(id: CyclistId): UserEntity? = users[id]

    override fun exist(username: String): Boolean =
        users.entries.any { it.value.username == username }
}