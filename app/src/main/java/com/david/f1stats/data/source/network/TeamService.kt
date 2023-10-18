package com.david.f1stats.data.source.network

import com.david.f1stats.data.model.teamDetail.TeamDetailData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import com.david.f1stats.data.model.base.Result
import javax.inject.Inject

class TeamService @Inject constructor(private val api:APIClient){
    suspend fun getTeamDetail(id: Int): Result<TeamDetailData> {
        return withContext(Dispatchers.IO) {
            try {
                val response = api.getTeamDetail(id)
                if(response.isSuccessful)
                    Result.Success(response.body()?.response?.firstOrNull() ?: TeamDetailData())
                else
                    Result.Error(Exception(response.message()))
            }
            catch (e: Exception) {
                Result.Error(e)
            }
        }
    }
}
