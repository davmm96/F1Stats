package com.david.f1stats.data.source.network

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

interface APIClient {

    companion object {
        const val URL_RACES = "races"
        const val URL_RANKING_DRIVERS = "rankings/drivers"
        const val URL_RANKING_TEAMS = "rankings/teams"
        const val URL_DRIVERS = "drivers"
        const val URL_TEAMS = "teams"
        const val URL_RACE_RESULT = "rankings/races"
        const val URL_CIRCUITS = "circuits"
        const val URL_SEASONS = "seasons"
        const val QUERY_PARAM_TYPE_RACE = "race"
        const val QUERY_PARAM_NUM_GP = 23
    }

    @GET(URL_RACES)
    suspend fun getNextRaces(
        @Query("season") season: String,
        @Query("type") type: String = QUERY_PARAM_TYPE_RACE,
        @Query("next") last: Int = QUERY_PARAM_NUM_GP,
        @Query("timezone") timezone: String = Constants.TIMEZONE
    ): Response<RaceResponse>

    @GET(URL_RACES)
    suspend fun getRaceDetails(
        @Query("competition") competition: Int,
        @Query("season") season: String,
        @Query("timezone") timezone: String = Constants.TIMEZONE
    ): Response<RaceResponse>

    @GET(URL_RACES)
    suspend fun getCompletedRaces(
        @Query("season") season: String,
        @Query("type") type: String = QUERY_PARAM_TYPE_RACE,
        @Query("last") last: Int = QUERY_PARAM_NUM_GP
    ): Response<RaceResponse>

    @GET(URL_RANKING_DRIVERS)
    suspend fun getRankingDrivers(
        @Query("season") season: String
    ): Response<RankingDriverResponse>

    @GET(URL_CIRCUITS)
    suspend fun getCircuits(): Response<CircuitResponse>

    @GET(URL_RANKING_TEAMS)
    suspend fun getRankingTeams(
        @Query("season") season: String
    ): Response<RankingTeamResponse>

    @GET(URL_DRIVERS)
    suspend fun getDriverDetail(
        @Query("id") id: Int
    ): Response<DriverDetailResponse>

    @GET(URL_TEAMS)
    suspend fun getTeamDetail(
        @Query("id") id: Int
    ): Response<TeamDetailResponse>

    @GET(URL_RACE_RESULT)
    suspend fun getRaceResult(
        @Query("race") idRace: Int
    ): Response<RaceResultResponse>

    @GET(URL_SEASONS)
    suspend fun getSeasons(): Response<SeasonResponse>
}
