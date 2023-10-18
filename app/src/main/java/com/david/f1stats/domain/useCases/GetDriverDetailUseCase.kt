package com.david.f1stats.domain.useCases

import com.david.f1stats.data.model.base.Result
import com.david.f1stats.data.repository.DriverRepository
import com.david.f1stats.domain.model.DriverDetail
import javax.inject.Inject

class GetDriverDetailUseCase @Inject constructor(
    private val repository: DriverRepository) {
    suspend operator fun invoke(id: Int): Result<DriverDetail> = repository.getDriverDetail(id)
}
