package com.david.f1stats.domain.useCases

import com.david.f1stats.domain.model.DriverDetail
import com.david.f1stats.domain.model.Result
import com.david.f1stats.domain.repository.DriverRepository

class GetDriverDetailUseCase(
    private val repository: DriverRepository
) {
    suspend operator fun invoke(id: Int): Result<DriverDetail> = repository.getDriverDetail(id)
}
