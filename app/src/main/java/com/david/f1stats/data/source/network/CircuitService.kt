package com.david.f1stats.data.source.network

import com.david.f1stats.data.model.circuit.CircuitData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import com.david.f1stats.data.model.base.Result

class CircuitService @Inject constructor(private val api:APIClient){
    suspend fun getCircuits(): Result<List<CircuitData>>{
        return withContext(Dispatchers.IO) {
            try {
                val response = api.getCircuits()
                if(response.isSuccessful)
                    Result.Success( response.body()?.response ?: emptyList())
                else
                    Result.Error(Exception(response.message()))
            }
            catch (e: Exception) {
                Result.Error(e)
            }
        }
    }
}