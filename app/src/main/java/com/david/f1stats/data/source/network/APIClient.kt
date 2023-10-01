package com.david.f1stats.data.source.network

import com.david.f1stats.BuildConfig
import com.david.f1stats.data.model.circuit.CircuitResponse
import retrofit2.http.GET
import retrofit2.http.Query
import com.david.f1stats.data.model.race.RaceResponse
import com.david.f1stats.data.model.rankingDriver.RankingDriverResponse
import com.david.f1stats.data.model.rankingTeam.RankingTeamResponse
import com.david.f1stats.utils.Constants
import com.david.f1stats.utils.Constants.URL_RACES
import retrofit2.Response
import retrofit2.http.Headers
import java.util.Calendar

interface APIClient {
    @Headers(BuildConfig.API_KEY_HEADER)
    @GET(URL_RACES)
    suspend fun getCurrentRaces(
        @Query("type") type: String = "race",
        @Query("season") season: String = Calendar.getInstance().get(Calendar.YEAR).toString()
    ): Response<RaceResponse>

    @Headers(BuildConfig.API_KEY_HEADER)
    @GET(URL_RACES)
    suspend fun getRaceDetails(
        @Query("competition") competition: Int,
        @Query("season") season: String = Calendar.getInstance().get(Calendar.YEAR).toString()
    ): Response<RaceResponse>

    @Headers(BuildConfig.API_KEY_HEADER)
    @GET(Constants.URL_RANKING_DRIVERS)
    suspend fun getCurrentRankingDrivers(
        @Query("season") season: String = Calendar.getInstance().get(Calendar.YEAR).toString()
    ): Response<RankingDriverResponse>

    @Headers(BuildConfig.API_KEY_HEADER)
    @GET(Constants.URL_CIRCUITS)
    suspend fun getCircuits(): Response<CircuitResponse>

    @Headers(BuildConfig.API_KEY_HEADER)
    @GET(Constants.URL_RANKING_TEAMS)
    suspend fun getCurrentRankingTeams(
        @Query("season") season: String = Calendar.getInstance().get(Calendar.YEAR).toString()
    ): Response<RankingTeamResponse>
}
