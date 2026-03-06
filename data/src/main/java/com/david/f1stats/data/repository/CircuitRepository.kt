package com.david.f1stats.data.repository

import com.david.f1stats.data.mapper.CircuitMapper
import com.david.f1stats.domain.model.Result
import com.david.f1stats.data.source.network.CircuitService
import com.david.f1stats.domain.model.Circuit
import com.david.f1stats.domain.repository.CircuitRepository as ICircuitRepository

class CircuitRepository(
    private val circuitService: CircuitService,
    private val circuitMapper: CircuitMapper,
) : ICircuitRepository {
    override suspend fun getCircuits(): Result<List<Circuit>> {
        return when (val response = circuitService.getCircuits()) {
            is Result.Success -> {
                Result.Success(circuitMapper.fromMap(response.data) ?: emptyList())
            }

            is Result.Error -> {
                response
            }
        }
    }
}
