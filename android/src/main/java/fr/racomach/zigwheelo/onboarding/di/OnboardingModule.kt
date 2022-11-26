package fr.racomach.zigwheelo.repository.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import fr.racomach.api.ZigWheeloApi
import fr.racomach.api.createApi
import fr.racomach.api.onboard.usecase.OnboardUser
import fr.racomach.zigwheelo.BuildConfig
import fr.racomach.zigwheelo.utils.isEmulator

@Module
@InstallIn(SingletonComponent::class)
abstract class OnboardingModule {

    companion object {
        @Provides
        fun provideApi(
            // Potential dependencies of this type
        ): ZigWheeloApi {
            return createApi(
                if (BuildConfig.DEBUG)
                    if (isEmulator())
                        "http://10.0.2.2:9580"
                    else
                        "http://192.168.1.11:9580"
                else "http://www.zigwheelo.com",
                BuildConfig.DEBUG
            )
        }

        @Provides
        fun provideOnboardUser(api: ZigWheeloApi): OnboardUser {
            return OnboardUser(api)
        }
    }
}
