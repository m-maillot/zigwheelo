package fr.racomach.zigwheelo.repository.di

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import fr.racomach.api.createApi
import fr.racomach.api.cyclist.CyclistApi
import fr.racomach.zigwheelo.BuildConfig
import fr.racomach.zigwheelo.repository.CyclistRepository
import fr.racomach.zigwheelo.repository.CyclistRepositoryImpl
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
    }

    @Binds
    abstract fun bindCyclistRepository(
        impl: CyclistRepositoryImpl
    ): CyclistRepository

}
