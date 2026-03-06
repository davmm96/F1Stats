package com.david.f1stats.domain.useCases

import com.david.f1stats.domain.model.Result
import com.david.f1stats.domain.model.TeamDetail
import com.david.f1stats.domain.repository.TeamRepository

class GetTeamDetailUseCase(
    private val repository: TeamRepository
) {
    suspend operator fun invoke(id: Int): Result<TeamDetail> = repository.getTeamDetail(id)
}
