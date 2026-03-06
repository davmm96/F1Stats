package com.david.f1stats.ui.ranking

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.david.f1stats.ui.R
import com.david.f1stats.ui.ranking.drivers.RankingDriversScreen
import com.david.f1stats.ui.ranking.drivers.RankingDriversViewModel
import com.david.f1stats.ui.ranking.raceResult.RankingRacesScreen
import com.david.f1stats.ui.ranking.raceResult.RankingRacesViewModel
import com.david.f1stats.ui.ranking.teams.RankingTeamsScreen
import com.david.f1stats.ui.ranking.teams.RankingTeamsViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Composable
fun RankingScreen(
    currentSeason: String,
    onDriverClick: (Int) -> Unit,
    onTeamClick: (Int) -> Unit,
    onRaceClick: (Int, String) -> Unit,
    modifier: Modifier = Modifier
) {
    val tabs = listOf(
        stringResource(R.string.ranking_drivers),
        stringResource(R.string.ranking_teams),
        stringResource(R.string.ranking_races)
    )
    val pagerState = rememberPagerState(pageCount = { tabs.size })
    val coroutineScope = rememberCoroutineScope()

    Column(modifier = modifier.fillMaxSize()) {
        TabRow(selectedTabIndex = pagerState.currentPage) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    selected = pagerState.currentPage == index,
                    onClick = {
                        coroutineScope.launch { pagerState.animateScrollToPage(index) }
                    },
                    text = {
                        val selected = pagerState.currentPage == index
                        Text(
                            text = title.uppercase(),
                            style = MaterialTheme.typography.labelMedium,
                            color = if (selected) MaterialTheme.colorScheme.primary
                            else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                        )
                    }
                )
            }
        }

        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize()
        ) { page ->
            when (page) {
                0 -> DriversPage(
                    currentSeason = currentSeason,
                    onDriverClick = onDriverClick
                )

                1 -> TeamsPage(
                    currentSeason = currentSeason,
                    onTeamClick = onTeamClick
                )

                2 -> RacesPage(
                    currentSeason = currentSeason,
                    onRaceClick = onRaceClick
                )
            }
        }
    }
}

@Composable
private fun DriversPage(
    currentSeason: String,
    onDriverClick: (Int) -> Unit
) {
    val viewModel: RankingDriversViewModel = koinViewModel()
    val drivers by viewModel.rankingDriverList.collectAsStateWithLifecycle()
    val isLoading by viewModel.isLoading.collectAsStateWithLifecycle()
    val errorMessage by viewModel.errorMessage.collectAsStateWithLifecycle()

    LaunchedEffect(currentSeason) {
        viewModel.fetchRankingDrivers()
    }

    RankingDriversScreen(
        drivers = drivers,
        isLoading = isLoading,
        errorMessage = errorMessage,
        onDriverClick = onDriverClick,
        onErrorConsumed = viewModel::clearErrorMessage
    )
}

@Composable
private fun TeamsPage(
    currentSeason: String,
    onTeamClick: (Int) -> Unit
) {
    val viewModel: RankingTeamsViewModel = koinViewModel()
    val teams by viewModel.rankingTeamList.collectAsStateWithLifecycle()
    val isLoading by viewModel.isLoading.collectAsStateWithLifecycle()
    val errorMessage by viewModel.errorMessage.collectAsStateWithLifecycle()

    LaunchedEffect(currentSeason) {
        viewModel.fetchRankingTeams()
    }

    RankingTeamsScreen(
        teams = teams,
        isLoading = isLoading,
        errorMessage = errorMessage,
        onTeamClick = onTeamClick,
        onErrorConsumed = viewModel::clearErrorMessage
    )
}

@Composable
private fun RacesPage(
    currentSeason: String,
    onRaceClick: (Int, String) -> Unit
) {
    val viewModel: RankingRacesViewModel = koinViewModel()
    val races by viewModel.racesCompletedList.collectAsStateWithLifecycle()
    val favoriteRacesIds by viewModel.favoriteRacesIds.collectAsStateWithLifecycle()
    val isLoading by viewModel.isLoading.collectAsStateWithLifecycle()
    val errorMessage by viewModel.errorMessage.collectAsStateWithLifecycle()
    val favoriteMessage by viewModel.favoriteMessage.collectAsStateWithLifecycle()

    LaunchedEffect(currentSeason) {
        viewModel.getRacesCompleted()
    }

    RankingRacesScreen(
        races = races,
        favoriteRacesIds = favoriteRacesIds,
        isLoading = isLoading,
        errorMessage = errorMessage,
        favoriteMessage = favoriteMessage,
        onFavClick = { race ->
            if (favoriteRacesIds.contains(race.idRace)) {
                viewModel.removeRaceFromFavorites(race.idRace)
            } else {
                viewModel.addRaceToFavorites(race)
            }
        },
        onRaceClick = onRaceClick,
        onErrorConsumed = viewModel::clearErrorMessage,
        onFavoriteMessageConsumed = viewModel::clearFavoriteMessage
    )
}
