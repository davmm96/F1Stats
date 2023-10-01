package com.david.f1stats.domain

import com.david.f1stats.data.repository.CircuitRepository
import com.david.f1stats.domain.model.Circuit
import javax.inject.Inject

class GetCircuitsUseCase @Inject constructor(private val repository: CircuitRepository) {
    suspend operator fun invoke(): List<Circuit>? = repository.getCircuits()
}