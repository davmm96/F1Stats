package com.david.f1stats.domain.useCases

import com.david.f1stats.data.repository.TeamRepository
import com.david.f1stats.domain.model.TeamDetail
import javax.inject.Inject

class GetTeamDetailUseCase @Inject constructor(
    private val repository: TeamRepository) {
    suspend operator fun invoke(id: Int): TeamDetail = repository.getTeamDetail(id)
}