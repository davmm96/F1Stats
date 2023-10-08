package com.david.f1stats.data.source.network

import com.david.f1stats.data.model.driverDetail.DriverDetailData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DriverService @Inject constructor(private val api:APIClient){
    suspend fun getDriverDetail(id: Int): DriverDetailData{
        return withContext(Dispatchers.IO) {
            val response = api.getDriverDetail(id)
            response.body()?.response?.get(0) ?: DriverDetailData()
        }
    }
}