package fr.racomach.zigwheelo.notifications

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import dagger.hilt.android.qualifiers.ApplicationContext
import fr.racomach.zigwheelo.R
import javax.inject.Inject

interface NotificationRepository {
    fun saveToken(token: String)
    fun getToken(): String?
    fun createDailyChannel()
}

class NotificationHandler @Inject constructor(
    @ApplicationContext private val context: Context,
) : NotificationRepository {
    override fun saveToken(token: String) {
        TODO("Not yet implemented")
    }

    override fun getToken(): String? {
        TODO("Not yet implemented")
    }

    override fun createDailyChannel() {
        // Create the NotificationChannel
        val channel = NotificationChannel(
            context.getString(R.string.daily_conditions_notification_channel_id),
            "Résumé quotidien",
            NotificationManager.IMPORTANCE_DEFAULT
        )
        channel.description =
            "Indique chaque jour un résumé des conditions de vos trajets sur la journée"
        // Register the channel with the system; you can't change the importance
        // or other notification behaviors after this
        val notificationManager =
            context.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }

}