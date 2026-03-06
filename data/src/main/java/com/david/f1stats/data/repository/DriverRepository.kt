package com.david.f1stats.data.repository

import com.david.f1stats.data.mapper.DriverDetailMapper
import com.david.f1stats.domain.model.Result
import com.david.f1stats.data.source.network.DriverService
import com.david.f1stats.domain.model.DriverDetail
import com.david.f1stats.domain.repository.DriverRepository as IDriverRepository

class DriverRepository(
    private val driverService: DriverService,
    private val driverDetailMapper: DriverDetailMapper,
) : IDriverRepository {
    override suspend fun getDriverDetail(id: Int): Result<DriverDetail> {
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
