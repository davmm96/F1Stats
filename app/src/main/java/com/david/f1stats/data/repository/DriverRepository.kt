package com.david.f1stats.data.repository

import com.david.f1stats.data.mapper.DriverDetailMapper
import com.david.f1stats.data.model.base.Result
import com.david.f1stats.data.source.network.DriverService
import com.david.f1stats.domain.model.DriverDetail

class DriverRepository constructor(
    private val driverService: DriverService,
    private val driverDetailMapper: DriverDetailMapper,
) {
    suspend fun getDriverDetail(id: Int): Result<DriverDetail> {
        return when (val response = driverService.getDriverDetail(id)) {
            is Result.Success -> {
                Result.Success(driverDetailMapper.fromMap(response.data))
            }

            is Result.Error -> {
                response
            }
        }
    }
}
