package com.david.f1stats.di

import android.content.Context
import com.david.f1stats.utils.MusicHelper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MusicModule {

    @Provides
    @Singleton
    fun provideMusicManager(@ApplicationContext context: Context): MusicHelper {
        return MusicHelper(context)
    }
}
