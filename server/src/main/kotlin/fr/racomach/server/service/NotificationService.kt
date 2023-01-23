package fr.racomach.server.service

import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.Message
import fr.racomach.event.sourcing.AggregateId
import fr.racomach.event.sourcing.EventHandler
import fr.racomach.event.sourcing.event.CyclistEvent
import java.util.logging.Logger

class NotificationService(
) : EventHandler<CyclistEvent.NotificationReady> {

    override fun on(id: AggregateId, event: CyclistEvent.NotificationReady) {
        Logger.getGlobal().info("Sending notification to ${event.token}")
        val message: Message = Message.builder()
            .putData("name", event.name)
            .setToken(event.token)
            .build()

        runCatching { FirebaseMessaging.getInstance().send(message) }
            .onFailure {
                Logger.getGlobal().warning("Failed to send the notification ${it.localizedMessage}")
            }.onSuccess {
                Logger.getGlobal().info("Notification send $it")
            }
    }

    override fun supportedEvents() = listOf(CyclistEvent.NotificationReady::class.qualifiedName!!)
}