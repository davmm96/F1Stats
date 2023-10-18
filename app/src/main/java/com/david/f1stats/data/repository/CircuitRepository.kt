package com.david.f1stats.data.repository

import com.david.f1stats.data.mapper.CircuitMapper
import com.david.f1stats.data.source.network.CircuitService
import com.david.f1stats.domain.model.Circuit
import javax.inject.Inject
import com.david.f1stats.data.model.base.Result

class CircuitRepository @Inject constructor(
    private val circuitService: CircuitService,
    private val circuitMapper: CircuitMapper,
){
    suspend fun getCircuits(): Result<List<Circuit>>{
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