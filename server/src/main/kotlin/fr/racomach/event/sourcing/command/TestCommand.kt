package fr.racomach.event.sourcing.command

import fr.racomach.event.sourcing.AggregateId

/**
 * For testing purpose only
 */
sealed class TestCommand(
    private val testId: AggregateId,
    override val createCommand: Boolean = false,
) : Command {
    override val id: AggregateId
        get() = testId

    companion object {
        private fun generateId(): AggregateId {
            return AggregateId()
        }
    }

    data class Create(val name: String) : TestCommand(generateId(), createCommand = true)

    data class ChangeAddress(
        val testId: AggregateId,
        val address: String,
    ) : TestCommand(testId)
}
