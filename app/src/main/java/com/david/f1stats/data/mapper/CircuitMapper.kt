package com.david.f1stats.data.mapper

import com.david.f1stats.data.model.circuit.CircuitData
import com.david.f1stats.domain.model.Circuit
import javax.inject.Inject

class CircuitMapper @Inject constructor(): IMapper<List<CircuitData>?, List<Circuit>?> {
    override fun fromMap(from: List<CircuitData>?): List<Circuit>? {
        return from?.map { it.toCircuit() }?.sortedBy { it.name }
    }

    private fun CircuitData.toCircuit() = Circuit(
        id = id ?: 0,
        name = name ?: "Circuit name not found",
        country = competition?.location?.country ?: "Country not found",
        length = length ?: "Length not found",
        laps = laps.toString(),
        firstGP = first_grand_prix.toString(),
        lapRecordTime = lap_record?.time ?: "Lap record time not found",
        lapRecordDriver = lap_record?.driver ?: "Lap record driver not found",
        imageURL = image ?: "Image not found"
    )
}
