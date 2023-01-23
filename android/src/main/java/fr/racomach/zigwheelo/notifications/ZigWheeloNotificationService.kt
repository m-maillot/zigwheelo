package fr.racomach.zigwheelo.notifications

import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class ZigWheeloNotificationService : FirebaseMessagingService() {

    override fun onNewToken(token: String) {
        Log.i("MMA-DEBUG", "Received the token $token")
        // TODO Save the token to the database
    }

    override fun onMessageReceived(message: RemoteMessage) {
        Log.i("MMA-DEBUG", "Received message ${message.data}")
    }
}