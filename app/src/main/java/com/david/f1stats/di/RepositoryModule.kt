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
import com.david.f1stats.domain.repository.CircuitRepository as ICircuitRepository
import com.david.f1stats.domain.repository.DriverRepository as IDriverRepository
import com.david.f1stats.domain.repository.FavoriteRaceRepository as IFavoriteRaceRepository
import com.david.f1stats.domain.repository.RaceRepository as IRaceRepository
import com.david.f1stats.domain.repository.RankingRepository as IRankingRepository
import com.david.f1stats.domain.repository.SeasonRepository as ISeasonRepository
import com.david.f1stats.domain.repository.TeamRepository as ITeamRepository

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
    single<ICircuitRepository> { CircuitRepository(get(), get()) }
    single<IDriverRepository> { DriverRepository(get(), get()) }
    single<IFavoriteRaceRepository> { FavoriteRaceRepository(get(), get()) }
    single<IRaceRepository> { RaceRepository(get(), get(), get(), get()) }
    single<IRankingRepository> { RankingRepository(get(), get(), get()) }
    single<ISeasonRepository> { SeasonRepository(get(), get()) }
    single<ITeamRepository> { TeamRepository(get(), get()) }
}
