package com.david.f1stats.data.source.local

import com.david.f1stats.domain.model.Race
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RaceProvider @Inject constructor() {
    var races: List<Race>? = emptyList()
}
