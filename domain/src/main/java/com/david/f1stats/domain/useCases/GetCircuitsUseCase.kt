package com.david.f1stats.domain.useCases

import com.david.f1stats.domain.model.Circuit
import com.david.f1stats.domain.model.Result
import com.david.f1stats.domain.repository.CircuitRepository

class GetCircuitsUseCase(
    private val repository: CircuitRepository
) {
    suspend operator fun invoke(): Result<List<Circuit>> = repository.getCircuits()
}
