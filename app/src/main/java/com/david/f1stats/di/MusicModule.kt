package com.david.f1stats.di

import com.david.f1stats.utils.MusicHelper
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val musicModule = module {
    single { MusicHelper(androidContext()) }
}
