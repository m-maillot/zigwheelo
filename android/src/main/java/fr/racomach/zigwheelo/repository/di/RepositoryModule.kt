package fr.racomach.zigwheelo.repository.di

import android.content.Context
import android.content.SharedPreferences
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import fr.racomach.api.createApi
import fr.racomach.api.cyclist.CyclistApi
import fr.racomach.zigwheelo.BuildConfig
import fr.racomach.zigwheelo.repository.CyclistRepository
import fr.racomach.zigwheelo.repository.CyclistRepositoryImpl
import fr.racomach.zigwheelo.storage.CyclistSharedPrefs
import fr.racomach.zigwheelo.storage.CyclistStorage
import fr.racomach.zigwheelo.utils.isEmulator

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    companion object {
        @Provides
        fun provideCyclistApi(
            // Potential dependencies of this type
        ): CyclistApi {
            return createApi(
                if (BuildConfig.DEBUG)
                    if (isEmulator())
                        "http://10.0.2.2:9580"
                    else
                        "http://192.168.1.11:9580"
                else "http://www.zigwheelo.com",
                BuildConfig.DEBUG
            ).cyclist
        }

        @Provides
        fun sharedPrefs(@ApplicationContext context: Context): SharedPreferences {
            return context.getSharedPreferences("zigwheelo", Context.MODE_PRIVATE)
        }
    }

    @Binds
    abstract fun bindCyclistRepository(
        impl: CyclistRepositoryImpl
    ): CyclistRepository

    @Binds
    abstract fun bindCyclistStorage(
        impl: CyclistSharedPrefs
    ): CyclistStorage


}
