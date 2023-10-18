package com.david.f1stats.data.source.network

import com.david.f1stats.data.model.driverDetail.DriverDetailData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import com.david.f1stats.data.model.base.Result

class DriverService @Inject constructor(private val api:APIClient){
    suspend fun getDriverDetail(id: Int): Result<DriverDetailData>{
        return withContext(Dispatchers.IO) {
            try {
                val response = api.getDriverDetail(id)
                if(response.isSuccessful)
                    Result.Success( response.body()?.response?.firstOrNull() ?: DriverDetailData())
                else
                    Result.Error(Exception(response.message()))
            }
            catch (e: Exception) {
                Result.Error(e)
            }
        }
    }
}