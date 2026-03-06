package com.david.f1stats.di

import com.david.f1stats.BuildConfig
import com.david.f1stats.data.source.network.APIClient
import com.david.f1stats.data.source.network.CircuitService
import com.david.f1stats.data.source.network.DriverService
import com.david.f1stats.data.source.network.RaceService
import com.david.f1stats.data.source.network.RankingService
import com.david.f1stats.data.source.network.SeasonService
import com.david.f1stats.data.source.network.TeamService
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val networkModule = module {
    single<OkHttpClient> {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        val headerInterceptor = Interceptor { chain ->
            val request = chain.request().newBuilder()
                .addHeader("x-apisports-key", BuildConfig.API_KEY)
                .build()
            chain.proceed(request)
        }
        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(headerInterceptor)
            .build()
    }

    single<Retrofit> {
        Retrofit.Builder()
            .baseUrl(BuildConfig.API_BASE)
            .addConverterFactory(GsonConverterFactory.create())
            .client(get())
            .build()
    }

    single<APIClient> { get<Retrofit>().create(APIClient::class.java) }

    single { CircuitService(get()) }
    single { DriverService(get()) }
    single { RaceService(get(), get()) }
    single { RankingService(get(), get()) }
    single { TeamService(get()) }
    single { SeasonService(get()) }
}
