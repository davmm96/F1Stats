package com.david.f1stats.di

import coil3.ImageLoader
import coil3.network.okhttp.OkHttpNetworkFetcherFactory
import com.david.f1stats.utils.CalendarHelper
import com.david.f1stats.utils.DialogHelper
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val imageLoaderModule = module {
    single {
        ImageLoader.Builder(androidContext())
            .components {
                add(OkHttpNetworkFetcherFactory())
            }
            .build()
    }

    single { DialogHelper() }
    single { CalendarHelper() }
}
