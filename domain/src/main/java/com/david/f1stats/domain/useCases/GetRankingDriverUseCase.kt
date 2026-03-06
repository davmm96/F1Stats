package com.david.f1stats.domain.useCases

import com.david.f1stats.domain.model.RankingDriver
import com.david.f1stats.domain.model.Result
import com.david.f1stats.domain.repository.RankingRepository

class GetRankingDriverUseCase(
    private val repository: RankingRepository
) {
    suspend operator fun invoke(): Result<List<RankingDriver>> = repository.getRankingDriver()
}
