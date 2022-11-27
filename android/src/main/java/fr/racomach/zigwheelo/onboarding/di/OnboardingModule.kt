package fr.racomach.zigwheelo.onboarding.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import fr.racomach.api.ZigWheeloDependencies
import fr.racomach.api.create
import fr.racomach.api.onboard.usecase.OnboardUser
import fr.racomach.zigwheelo.BuildConfig
import fr.racomach.zigwheelo.utils.isEmulator

@Module
@InstallIn(SingletonComponent::class)
abstract class OnboardingModule {

    companion object {
        @Provides
        fun provideDependencies(
            @ApplicationContext context: Context,
        ): ZigWheeloDependencies {
            return ZigWheeloDependencies.Companion.create(
                context,
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
        fun provideOnboardUser(dependencies: ZigWheeloDependencies): OnboardUser {
            return OnboardUser(dependencies)
        }
    }
}
