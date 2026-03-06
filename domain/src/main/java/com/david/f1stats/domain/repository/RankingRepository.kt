package com.david.f1stats.domain.repository

import com.david.f1stats.domain.model.RankingDriver
import com.david.f1stats.domain.model.RankingTeam
import com.david.f1stats.domain.model.Result

interface RankingRepository {
    suspend fun getRankingDriver(): Result<List<RankingDriver>>
    suspend fun getRankingTeam(): Result<List<RankingTeam>>
}
