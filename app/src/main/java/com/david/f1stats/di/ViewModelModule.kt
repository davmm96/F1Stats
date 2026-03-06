package com.david.f1stats.di

import com.david.f1stats.ui.SharedViewModel
import com.david.f1stats.ui.circuits.CircuitsViewModel
import com.david.f1stats.ui.favorites.FavoritesViewModel
import com.david.f1stats.ui.races.RacesViewModel
import com.david.f1stats.ui.races.raceDetail.RaceDetailViewModel
import com.david.f1stats.ui.ranking.drivers.RankingDriversViewModel
import com.david.f1stats.ui.ranking.drivers.driverDetail.DriverDetailViewModel
import com.david.f1stats.ui.ranking.raceResult.RankingRacesViewModel
import com.david.f1stats.ui.ranking.raceResult.raceResultDetail.RaceResultViewModel
import com.david.f1stats.ui.ranking.teams.RankingTeamsViewModel
import com.david.f1stats.ui.ranking.teams.teamDetail.TeamDetailViewModel
import com.david.f1stats.ui.settings.SettingsViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { SharedViewModel(get()) }
    viewModel { CircuitsViewModel(get()) }
    viewModel { FavoritesViewModel(get(), get()) }
    viewModel { RacesViewModel(get()) }
    viewModel { RaceDetailViewModel(get()) }
    viewModel { RankingDriversViewModel(get()) }
    viewModel { DriverDetailViewModel(get()) }
    viewModel { RankingRacesViewModel(get(), get(), get(), get()) }
    viewModel { RaceResultViewModel(get()) }
    viewModel { RankingTeamsViewModel(get()) }
    viewModel { TeamDetailViewModel(get()) }
    viewModel { SettingsViewModel(get(), get(), get()) }
}
