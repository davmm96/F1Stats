package com.david.f1stats.data.source.network

import com.david.f1stats.data.model.teamDetail.TeamDetailData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class TeamService @Inject constructor(private val api:APIClient){
    suspend fun getTeamDetail(id: Int): TeamDetailData {
        return withContext(Dispatchers.IO) {
            val response = api.getTeamDetail(id)
            response.body()?.response?.firstOrNull() ?: TeamDetailData()
        }
    }
}