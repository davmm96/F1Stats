package com.david.f1stats.data.source.network

import retrofit2.http.GET
import retrofit2.http.Query
import com.david.f1stats.data.model.race.RaceResponse
import com.david.f1stats.utils.Constants.URL_RACES
import retrofit2.Response
import retrofit2.http.Headers

interface RaceAPIClient {
    @Headers("x-apisports-key: 034da704a4fcc0f36f7f6c59b86529ff")//TODO Change this
    @GET(URL_RACES)
    suspend fun getRaces(
        @Query("type") type: String = "race",
        @Query("season") season: String = "2023"
    ): Response<RaceResponse>
}
