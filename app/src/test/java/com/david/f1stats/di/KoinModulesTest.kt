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
import com.david.f1stats.data.source.local.RaceDao
import com.david.f1stats.data.source.network.CircuitService
import com.david.f1stats.data.source.network.DriverService
import com.david.f1stats.data.source.network.RaceService
import com.david.f1stats.data.source.network.RankingService
import com.david.f1stats.data.source.network.SeasonService
import com.david.f1stats.data.source.network.TeamService
import com.david.f1stats.domain.useCases.DeleteFavoriteUseCase
import com.david.f1stats.domain.useCases.GetAllFavoriteRacesIdsUseCase
import com.david.f1stats.domain.useCases.GetAllFavoriteRacesUseCase
import com.david.f1stats.domain.useCases.GetCircuitsUseCase
import com.david.f1stats.domain.useCases.GetDriverDetailUseCase
import com.david.f1stats.domain.useCases.GetRaceCompletedUseCase
import com.david.f1stats.domain.useCases.GetRaceDetailsUseCase
import com.david.f1stats.domain.useCases.GetRaceResultUseCase
import com.david.f1stats.domain.useCases.GetRacesUseCase
import com.david.f1stats.domain.useCases.GetRankingDriverUseCase
import com.david.f1stats.domain.useCases.GetRankingTeamUseCase
import com.david.f1stats.domain.useCases.GetSeasonsUseCase
import com.david.f1stats.domain.useCases.GetTeamDetailUseCase
import com.david.f1stats.domain.useCases.InsertFavoriteRaceUseCase
import com.david.f1stats.utils.PreferencesHelper
import io.mockk.mockk
import org.junit.After
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.get

class KoinModulesTest : KoinTest {

    // Stub module providing mocked dependencies that would normally come from
    // networkModule (which requires BuildConfig) and databaseModule (which requires Context)
    private val stubInfraModule = module {
        single<CircuitService> { mockk() }
        single<DriverService> { mockk() }
        single<RaceService> { mockk() }
        single<RankingService> { mockk() }
        single<SeasonService> { mockk() }
        single<TeamService> { mockk() }
        single<RaceDao> { mockk() }
        single<PreferencesHelper> { mockk(relaxed = true) }
    }

    @After
    fun tearDown() {
        stopKoin()
    }

    @Test
    fun `repository module resolves all mappers`() {
        startKoin {
            modules(stubInfraModule, repositoryModule)
        }

        get<CircuitMapper>()
        get<DriverDetailMapper>()
        get<FavoriteRaceMapper>()
        get<RaceDetailMapper>()
        get<RaceMapper>()
        get<RaceResultMapper>()
        get<RankingDriverMapper>()
        get<RankingTeamMapper>()
        get<SeasonMapper>()
        get<TeamDetailMapper>()
    }

    @Test
    fun `repository module resolves all repository interfaces`() {
        startKoin {
            modules(stubInfraModule, repositoryModule)
        }

        get<com.david.f1stats.domain.repository.CircuitRepository>()
        get<com.david.f1stats.domain.repository.DriverRepository>()
        get<com.david.f1stats.domain.repository.FavoriteRaceRepository>()
        get<com.david.f1stats.domain.repository.RaceRepository>()
        get<com.david.f1stats.domain.repository.RankingRepository>()
        get<com.david.f1stats.domain.repository.SeasonRepository>()
        get<com.david.f1stats.domain.repository.TeamRepository>()
    }

    @Test
    fun `use case module resolves all use cases`() {
        startKoin {
            modules(stubInfraModule, repositoryModule, useCaseModule)
        }

        get<GetCircuitsUseCase>()
        get<GetRacesUseCase>()
        get<GetRankingDriverUseCase>()
        get<GetRankingTeamUseCase>()
        get<GetDriverDetailUseCase>()
        get<GetTeamDetailUseCase>()
        get<GetRaceDetailsUseCase>()
        get<GetRaceResultUseCase>()
        get<GetRaceCompletedUseCase>()
        get<GetSeasonsUseCase>()
        get<GetAllFavoriteRacesUseCase>()
        get<GetAllFavoriteRacesIdsUseCase>()
        get<InsertFavoriteRaceUseCase>()
        get<DeleteFavoriteUseCase>()
    }
}
