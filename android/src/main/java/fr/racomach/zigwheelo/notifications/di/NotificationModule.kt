package fr.racomach.zigwheelo.notifications.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import fr.racomach.zigwheelo.notifications.NotificationHandler
import fr.racomach.zigwheelo.notifications.NotificationRepository

@Module
@InstallIn(SingletonComponent::class)
interface NotificationModule {

    @Binds
    fun provideNotificationRepository(repository: NotificationHandler): NotificationRepository

}
