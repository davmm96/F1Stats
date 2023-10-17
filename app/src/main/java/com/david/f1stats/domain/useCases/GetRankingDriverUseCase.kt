package com.david.f1stats.domain.useCases

import com.david.f1stats.data.repository.RankingRepository
import com.david.f1stats.domain.model.RankingDriver
import javax.inject.Inject

class GetRankingDriverUseCase @Inject constructor(
    private val repository: RankingRepository) {
    suspend operator fun invoke(): List<RankingDriver> = repository.getRankingDriver()
}