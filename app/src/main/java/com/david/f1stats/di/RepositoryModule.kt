package com.david.f1stats.di

import com.david.f1stats.data.mapper.CircuitMapper
import com.david.f1stats.data.mapper.DriverDetailMapper
import com.david.f1stats.data.mapper.FavoriteRaceMapper
import com.david.f1stats.data.mapper.RaceDetailMapper
import com.david.f1stats.data.mapper.RaceMapper
import com.david.f1stats.data.mapper.RaceResultMapper
import com.david.f1stats.data.mapper.RankingDriverMapper
import com.david.f1stats.data.mapper.RankingTeamMapper
import com.david.f1stats.data.mapper.SeasonMapper
import com.david.f1stats.data.mapper.TeamDetailMapper
import com.david.f1stats.data.repository.CircuitRepository
import com.david.f1stats.data.repository.DriverRepository
import com.david.f1stats.data.repository.FavoriteRaceRepository
import com.david.f1stats.data.repository.RaceRepository
import com.david.f1stats.data.repository.RankingRepository
import com.david.f1stats.data.repository.SeasonRepository
import com.david.f1stats.data.repository.TeamRepository
import org.koin.dsl.module

val repositoryModule = module {
    // Mappers
    single { CircuitMapper() }
    single { DriverDetailMapper() }
    single { FavoriteRaceMapper() }
    single { RaceDetailMapper() }
    single { RaceMapper() }
    single { RaceResultMapper() }
    single { RankingDriverMapper() }
    single { RankingTeamMapper() }
    single { SeasonMapper() }
    single { TeamDetailMapper() }

    // Repositories
    single { CircuitRepository(get(), get()) }
    single { DriverRepository(get(), get()) }
    single { FavoriteRaceRepository(get(), get()) }
    single { RaceRepository(get(), get(), get(), get()) }
    single { RankingRepository(get(), get(), get()) }
    single { SeasonRepository(get(), get()) }
    single { TeamRepository(get(), get()) }
}
