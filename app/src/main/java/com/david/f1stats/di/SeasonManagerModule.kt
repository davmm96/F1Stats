package com.david.f1stats.di

import com.david.f1stats.utils.SeasonManager
import org.koin.dsl.module

val seasonManagerModule = module {
    single { SeasonManager(get()) }
}
