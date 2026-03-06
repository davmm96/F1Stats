package com.david.f1stats.domain.repository

import com.david.f1stats.domain.model.Result
import com.david.f1stats.domain.model.TeamDetail

interface TeamRepository {
    suspend fun getTeamDetail(id: Int): Result<TeamDetail>
}
