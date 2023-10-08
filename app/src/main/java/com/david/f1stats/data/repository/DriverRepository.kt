package com.david.f1stats.data.repository

import com.david.f1stats.data.mapper.DriverDetailMapper
import com.david.f1stats.data.source.network.DriverService
import com.david.f1stats.domain.model.DriverDetail
import javax.inject.Inject

class DriverRepository @Inject constructor(
    private val api: DriverService,
    private val driverDetailMapper: DriverDetailMapper,
){
    suspend fun getDriverDetail(id: Int): DriverDetail {
        val response = api.getDriverDetail(id)
        return driverDetailMapper.fromMap(response)
    }
}