package com.david.f1stats.domain.useCases

import com.david.f1stats.data.model.base.Result
import com.david.f1stats.data.repository.RankingRepository
import com.david.f1stats.domain.model.RankingTeam

class GetRankingTeamUseCase constructor(
    private val repository: RankingRepository
) {
    suspend operator fun invoke(): Result<List<RankingTeam>> = repository.getRankingTeam()
}
