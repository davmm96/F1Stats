package com.david.f1stats.domain.repository

import com.david.f1stats.domain.model.Circuit
import com.david.f1stats.domain.model.Result

interface CircuitRepository {
    suspend fun getCircuits(): Result<List<Circuit>>
}
