package com.david.f1stats.data.model.rankingDriver

data class RankingDriverData(
    val behind: Int,
    val driver: RankingDriverInfoData,
    val points: Int,
    val position: Int,
    val season: Int,
    val team: RankingDriverTeamData,
    val wins: Int
) 