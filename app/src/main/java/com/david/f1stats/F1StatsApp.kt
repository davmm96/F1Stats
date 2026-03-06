package com.david.f1stats

import android.app.Application
import com.david.f1stats.di.databaseModule
import com.david.f1stats.di.imageLoaderModule
import com.david.f1stats.di.musicModule
import com.david.f1stats.di.networkModule
import com.david.f1stats.di.repositoryModule
import com.david.f1stats.di.seasonManagerModule
import com.david.f1stats.di.sharedPreferencesModule
import com.david.f1stats.di.useCaseModule
import com.david.f1stats.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class F1StatsApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@F1StatsApp)
            modules(
                networkModule,
                databaseModule,
                sharedPreferencesModule,
                seasonManagerModule,
                imageLoaderModule,
                musicModule,
                repositoryModule,
                useCaseModule,
                viewModelModule
            )
        }
    }
}
