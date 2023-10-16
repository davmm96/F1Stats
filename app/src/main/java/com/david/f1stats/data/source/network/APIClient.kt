package com.david.f1stats.data.source.network

import com.david.f1stats.BuildConfig
import com.david.f1stats.data.model.circuit.CircuitResponse
import com.david.f1stats.data.model.driverDetail.DriverDetailResponse
import retrofit2.http.GET
import retrofit2.http.Query
import com.david.f1stats.data.model.race.RaceResponse
import com.david.f1stats.data.model.raceResult.RaceResultResponse
import com.david.f1stats.data.model.rankingDriver.RankingDriverResponse
import com.david.f1stats.data.model.rankingTeam.RankingTeamResponse
import com.david.f1stats.data.model.season.SeasonResponse
import com.david.f1stats.data.model.teamDetail.TeamDetailResponse
import com.david.f1stats.utils.Constants
import retrofit2.Response
import retrofit2.http.Headers

interface APIClient {
    @Headers(BuildConfig.API_KEY_HEADER)
    @GET(Constants.URL_RACES)
    suspend fun getNextRaces(
        @Query("season") season: String,
        @Query("type") type: String = Constants.TYPE_RACE_QUERY_PARAM,
        @Query("next") last: Int = Constants.NUM_GP_SEASON,
        @Query("timezone") timezone: String = Constants.TIMEZONE
    ): Response<RaceResponse>

    @Headers(BuildConfig.API_KEY_HEADER)
    @GET(Constants.URL_RACES)
    suspend fun getRaceDetails(
        @Query("competition") competition: Int,
        @Query("season") season: String,
        @Query("timezone") timezone: String = Constants.TIMEZONE
    ): Response<RaceResponse>

    @Headers(BuildConfig.API_KEY_HEADER)
    @GET(Constants.URL_RACES)
    suspend fun getCompletedRaces(
        @Query("season") season: String,
        @Query("type") type: String = Constants.TYPE_RACE_QUERY_PARAM,
        @Query("last") last: Int = Constants.NUM_GP_SEASON
    ): Response<RaceResponse>

    @Headers(BuildConfig.API_KEY_HEADER)
    @GET(Constants.URL_RANKING_DRIVERS)
    suspend fun getRankingDrivers(
        @Query("season") season: String
    ): Response<RankingDriverResponse>

    @Headers(BuildConfig.API_KEY_HEADER)
    @GET(Constants.URL_CIRCUITS)
    suspend fun getCircuits(): Response<CircuitResponse>

    @Headers(BuildConfig.API_KEY_HEADER)
    @GET(Constants.URL_RANKING_TEAMS)
    suspend fun getRankingTeams(
        @Query("season") season: String
    ): Response<RankingTeamResponse>

    @Headers(BuildConfig.API_KEY_HEADER)
    @GET(Constants.URL_DRIVERS)
    suspend fun getDriverDetail(
        @Query("id") id: Int
    ): Response<DriverDetailResponse>

    @Headers(BuildConfig.API_KEY_HEADER)
    @GET(Constants.URL_TEAMS)
    suspend fun getTeamDetail(
        @Query("id") id: Int
    ): Response<TeamDetailResponse>

    @Headers(BuildConfig.API_KEY_HEADER)
    @GET(Constants.URL_RACE_RESULT)
    suspend fun getRaceResult(
        @Query("race") idRace: Int
    ): Response<RaceResultResponse>

    @Headers(BuildConfig.API_KEY_HEADER)
    @GET(Constants.URL_SEASONS)
    suspend fun getSeasons(): Response<SeasonResponse>
}
