package com.david.f1stats.data.mapper

import com.david.f1stats.data.model.circuit.CircuitData
import com.david.f1stats.domain.model.Circuit
import javax.inject.Inject

class CircuitMapper @Inject constructor(): IMapper<List<CircuitData>?, List<Circuit>?> {
    override fun fromMap(from: List<CircuitData>?): List<Circuit>? {
        return from?.map { circuitData ->
            Circuit(
                id = circuitData.id ?: 0,
                name = circuitData.name ?: "Circuit name not found",
                country = circuitData.competition?.location?.country ?: "Country not found",
                length = circuitData.length ?: "Length not found",
                laps = circuitData.laps.toString(),
                firstGP = circuitData.first_grand_prix.toString(),
                lapRecordTime = circuitData.lap_record?.time ?: "Lap record time not found",
                lapRecordDriver = circuitData.lap_record?.driver ?: "Lap record driver not found",
            )
        }
    }
}