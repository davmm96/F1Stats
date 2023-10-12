package com.david.f1stats.di

import com.david.f1stats.utils.PreferencesHelper
import com.david.f1stats.utils.SeasonManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SeasonManagerModule {

    @Provides
    @Singleton
    fun provideSeasonManager(preferencesHelper: PreferencesHelper): SeasonManager {
        return SeasonManager(preferencesHelper)
    }
}
