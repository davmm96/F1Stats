package com.david.f1stats.data.source.network

import com.david.f1stats.data.model.circuit.CircuitData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CircuitService @Inject constructor(private val api:APIClient){
    suspend fun getCircuits():List<CircuitData>{
        return withContext(Dispatchers.IO) {
            val response = api.getCircuits()
            response.body()?.response ?: emptyList()
        }
    }
}