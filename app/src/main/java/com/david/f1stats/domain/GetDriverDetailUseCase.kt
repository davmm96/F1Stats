package com.david.f1stats.domain

import com.david.f1stats.data.repository.DriverRepository
import com.david.f1stats.domain.model.DriverDetail
import javax.inject.Inject

class GetDriverDetailUseCase @Inject constructor(private val repository: DriverRepository) {
    suspend operator fun invoke(id: Int): DriverDetail = repository.getDriverDetail(id)
}