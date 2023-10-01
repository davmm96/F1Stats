package com.david.f1stats.data.mapper

import com.david.f1stats.data.model.circuit.CircuitData
import com.david.f1stats.domain.model.Circuit
import javax.inject.Inject

class CircuitMapper @Inject constructor(): IMapper<List<CircuitData>?, List<Circuit>?> {
    override fun fromMap(from: List<CircuitData>?): List<Circuit>? {
        return from?.map { circuitData ->
            Circuit(
                id = circuitData.id,
                name = circuitData.name,
                country = circuitData.competition.location.country,
                length = circuitData.length,
                laps = circuitData.laps.toString(),
                firstGP = circuitData.first_grand_prix.toString(),
                lapRecordTime = circuitData.lap_record.time?:"No time",
                lapRecordDriver = circuitData.lap_record.driver?:"No driver",
            )
        }
    }
}