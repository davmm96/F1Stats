package com.david.f1stats.di

import android.content.Context
import com.david.f1stats.utils.PreferencesHelper
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val sharedPreferencesModule = module {
    single {
        androidContext().getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
    }

    single { PreferencesHelper(get()) }
}
