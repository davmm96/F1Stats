package com.david.f1stats.domain.useCases

import com.david.f1stats.data.repository.RankingRepository
import com.david.f1stats.domain.model.RankingTeam
import javax.inject.Inject

class GetRankingTeamUseCase @Inject constructor(
    private val repository: RankingRepository) {
    suspend operator fun invoke(): List<RankingTeam> = repository.getRankingTeam()
}