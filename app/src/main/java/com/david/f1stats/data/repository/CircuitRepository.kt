package com.david.f1stats.data.repository

import com.david.f1stats.data.mapper.CircuitMapper
import com.david.f1stats.data.source.network.CircuitService
import com.david.f1stats.domain.model.Circuit
import javax.inject.Inject

class CircuitRepository @Inject constructor(
    private val api: CircuitService,
    private val circuitMapper: CircuitMapper,
){
    suspend fun getCircuits(): List<Circuit>{
        val response = api.getCircuits()
        return circuitMapper.fromMap(response) ?: emptyList()
    }
}