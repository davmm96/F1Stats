package com.david.f1stats.di

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
import org.koin.dsl.module

val useCaseModule = module {
    factory { DeleteFavoriteUseCase(get()) }
    factory { GetAllFavoriteRacesIdsUseCase(get()) }
    factory { GetAllFavoriteRacesUseCase(get()) }
    factory { GetCircuitsUseCase(get()) }
    factory { GetDriverDetailUseCase(get()) }
    factory { GetRaceCompletedUseCase(get()) }
    factory { GetRaceDetailsUseCase(get()) }
    factory { GetRaceResultUseCase(get()) }
    factory { GetRacesUseCase(get()) }
    factory { GetRankingDriverUseCase(get()) }
    factory { GetRankingTeamUseCase(get()) }
    factory { GetSeasonsUseCase(get()) }
    factory { GetTeamDetailUseCase(get()) }
    factory { InsertFavoriteRaceUseCase(get()) }
}
