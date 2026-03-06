package com.david.f1stats.domain.repository

import com.david.f1stats.domain.model.DriverDetail
import com.david.f1stats.domain.model.Result

interface DriverRepository {
    suspend fun getDriverDetail(id: Int): Result<DriverDetail>
}
