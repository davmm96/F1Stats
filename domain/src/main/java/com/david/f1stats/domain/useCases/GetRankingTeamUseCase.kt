package com.david.f1stats.domain.useCases

import com.david.f1stats.domain.model.RankingTeam
import com.david.f1stats.domain.model.Result
import com.david.f1stats.domain.repository.RankingRepository

class GetRankingTeamUseCase(
    private val repository: RankingRepository
) {
    suspend operator fun invoke(): Result<List<RankingTeam>> = repository.getRankingTeam()
}
