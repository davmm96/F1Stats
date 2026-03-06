package com.david.f1stats.domain.useCases

import com.david.f1stats.data.model.base.Result
import com.david.f1stats.data.repository.CircuitRepository
import com.david.f1stats.domain.model.Circuit

class GetCircuitsUseCase constructor(
    private val repository: CircuitRepository
) {
    suspend operator fun invoke(): Result<List<Circuit>> = repository.getCircuits()
}
