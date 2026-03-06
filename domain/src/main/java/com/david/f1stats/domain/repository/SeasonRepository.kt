package com.david.f1stats.domain.repository

import com.david.f1stats.domain.model.Result
import com.david.f1stats.domain.model.Season

interface SeasonRepository {
    suspend fun getSeasons(): Result<List<Season>>
}
