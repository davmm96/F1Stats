package com.david.f1stats.data.model.driverDetail

data class DriverDetailResponse(
    val errors: List<Any>,
    val get: String,
    val parameters: DriverDetailParametersData,
    val response: List<DriverDetailData>,
    val results: Int
)