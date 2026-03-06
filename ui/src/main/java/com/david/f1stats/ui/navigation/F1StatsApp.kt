package com.david.f1stats.ui.navigation

import android.net.Uri
import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import coil3.ImageLoader
import com.david.f1stats.ui.R
import com.david.f1stats.ui.SharedViewModel
import com.david.f1stats.ui.circuits.CircuitsScreen
import com.david.f1stats.ui.circuits.CircuitsViewModel
import com.david.f1stats.ui.favorites.FavoritesScreen
import com.david.f1stats.ui.favorites.FavoritesViewModel
import com.david.f1stats.ui.races.RacesScreen
import com.david.f1stats.ui.races.RacesViewModel
import com.david.f1stats.ui.races.raceDetail.RaceDetailScreen
import com.david.f1stats.ui.races.raceDetail.RaceDetailViewModel
import com.david.f1stats.ui.ranking.RankingScreen
import com.david.f1stats.ui.ranking.drivers.driverDetail.DriverDetailScreen
import com.david.f1stats.ui.ranking.drivers.driverDetail.DriverDetailViewModel
import com.david.f1stats.ui.ranking.raceResult.raceResultDetail.RaceResultScreen
import com.david.f1stats.ui.ranking.raceResult.raceResultDetail.RaceResultViewModel
import com.david.f1stats.ui.ranking.teams.teamDetail.TeamDetailScreen
import com.david.f1stats.ui.ranking.teams.teamDetail.TeamDetailViewModel
import com.david.f1stats.ui.settings.SettingsScreen
import com.david.f1stats.ui.settings.SettingsViewModel
import com.david.f1stats.utils.CalendarHelper
import com.david.f1stats.utils.Constants
import com.david.f1stats.utils.DialogHelper
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.koinInject

private object Routes {
    const val RACES = "races"
    const val RANKING = "ranking"
    const val CIRCUITS = "circuits"
    const val FAVORITES = "favorites"
    const val SETTINGS = "settings"
    const val RACE_DETAIL = "race_detail/{id}/{country}"
    const val DRIVER_DETAIL = "driver_detail/{id}"
    const val TEAM_DETAIL = "team_detail/{id}"
    const val RACE_RESULT = "race_result/{id}/{country}"
    val mainTabs = setOf(RACES, RANKING, CIRCUITS, FAVORITES)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun F1StatsApp(sharedViewModel: SharedViewModel = koinViewModel()) {
    val navController = rememberNavController()
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry?.destination?.route
    val currentSeason by sharedViewModel.selectedSeason.collectAsStateWithLifecycle()

    var lastMainRoute by rememberSaveable { mutableStateOf(Routes.RACES) }
    LaunchedEffect(currentRoute) {
        if (currentRoute in Routes.mainTabs) lastMainRoute = currentRoute!!
    }

    val isMainTab = currentRoute in Routes.mainTabs
    val showBottomBar = currentRoute != Routes.SETTINGS
    val showBack = !isMainTab

    val title = when {
        currentRoute == Routes.RACES -> stringResource(R.string.title_calendar, currentSeason)
        currentRoute == Routes.RANKING -> stringResource(R.string.title_ranking, currentSeason)
        currentRoute == Routes.CIRCUITS -> stringResource(R.string.title_circuits)
        currentRoute == Routes.FAVORITES -> stringResource(R.string.title_favorites)
        currentRoute == Routes.SETTINGS -> stringResource(R.string.settings)
        currentRoute == Routes.RACE_DETAIL || currentRoute == Routes.RACE_RESULT ->
            currentBackStackEntry?.arguments?.getString("country") ?: ""

        else -> ""
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = title, style = MaterialTheme.typography.titleMedium)
                },
                navigationIcon = {
                    if (showBack) {
                        IconButton(onClick = { navController.navigateUp() }) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = stringResource(R.string.navigate_back)
                            )
                        }
                    }
                },
                actions = {
                    if (isMainTab) {
                        IconButton(onClick = { navController.navigate(Routes.SETTINGS) }) {
                            Icon(
                                painter = painterResource(R.drawable.icon_settings),
                                contentDescription = stringResource(R.string.settings),
                                tint = MaterialTheme.colorScheme.onPrimary
                            )
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimary,
                    actionIconContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        },
        bottomBar = {
            if (showBottomBar) {
                F1BottomBar(
                    selectedRoute = lastMainRoute,
                    onTabSelected = { route ->
                        navController.navigate(route) {
                            popUpTo(navController.graph.startDestinationId) { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Routes.RACES,
            modifier = Modifier
                .padding(innerPadding)
                .consumeWindowInsets(innerPadding)
        ) {

            composable(Routes.RACES) {
                val vm: RacesViewModel = koinViewModel()
                val races by vm.raceList.collectAsStateWithLifecycle()
                val isLoading by vm.isLoading.collectAsStateWithLifecycle()
                val isSeasonCompleted by vm.isSeasonCompleted.collectAsStateWithLifecycle()
                val errorMessage by vm.errorMessage.collectAsStateWithLifecycle()
                LaunchedEffect(currentSeason) { vm.fetchRaces() }
                RacesScreen(
                    races = races,
                    isLoading = isLoading,
                    isSeasonCompleted = isSeasonCompleted,
                    errorMessage = errorMessage,
                    onRaceClick = { id, country ->
                        navController.navigate("race_detail/$id/${Uri.encode(country)}")
                    },
                    onErrorConsumed = vm::clearErrorMessage
                )
            }

            composable(
                route = Routes.RACE_DETAIL,
                arguments = listOf(
                    navArgument("id") { type = NavType.IntType },
                    navArgument("country") { type = NavType.StringType }
                )
            ) { backStackEntry ->
                val id = backStackEntry.arguments!!.getInt("id")
                val calendarHelper: CalendarHelper = koinInject()
                val vm: RaceDetailViewModel = koinViewModel()
                val raceInfo by vm.raceInfo.collectAsStateWithLifecycle()
                val schedule by vm.raceList.collectAsStateWithLifecycle()
                val addToCalendarEvent by vm.addToCalendarEvent.collectAsStateWithLifecycle()
                val errorMessage by vm.errorMessage.collectAsStateWithLifecycle()
                val context = LocalContext.current
                LaunchedEffect(id) { vm.loadData(id) }
                LaunchedEffect(addToCalendarEvent) {
                    addToCalendarEvent?.let { calendarHelper.addToCalendar(context, it) }
                }
                RaceDetailScreen(
                    raceInfo = raceInfo,
                    schedule = schedule ?: emptyList(),
                    isLoading = schedule == null && raceInfo == null,
                    errorMessage = errorMessage,
                    onCalendarClick = { title, dateCalendar ->
                        vm.onAddToCalendarRequested(
                            CalendarHelper.CalendarEvent(
                                title = title,
                                description = raceInfo?.competition ?: "",
                                location = raceInfo?.circuit ?: "",
                                startMillis = dateCalendar
                            )
                        )
                    },
                    onErrorConsumed = vm::clearErrorMessage
                )
            }

            composable(Routes.RANKING) {
                RankingScreen(
                    currentSeason = currentSeason,
                    onDriverClick = { id -> navController.navigate("driver_detail/$id") },
                    onTeamClick = { id -> navController.navigate("team_detail/$id") },
                    onRaceClick = { id, country ->
                        navController.navigate("race_result/$id/${Uri.encode(country)}")
                    }
                )
            }

            composable(
                route = Routes.DRIVER_DETAIL,
                arguments = listOf(navArgument("id") { type = NavType.IntType })
            ) { backStackEntry ->
                val id = backStackEntry.arguments!!.getInt("id")
                val imageLoader: ImageLoader = koinInject()
                val dialogHelper: DialogHelper = koinInject()
                val activity = LocalActivity.current as FragmentActivity
                val vm: DriverDetailViewModel = koinViewModel()
                val driver by vm.driverInfo.collectAsStateWithLifecycle()
                val isLoading by vm.isLoading.collectAsStateWithLifecycle()
                val errorMessage by vm.errorMessage.collectAsStateWithLifecycle()
                LaunchedEffect(id) { vm.fetchDriverDetail(id) }
                DriverDetailScreen(
                    driver = driver,
                    isLoading = isLoading,
                    errorMessage = errorMessage,
                    onImageClick = { url ->
                        if (url != null && url != Constants.IMAGE_NOT_FOUND) {
                            dialogHelper.showImageDialog(activity, imageLoader, url)
                        }
                    },
                    onErrorConsumed = vm::clearErrorMessage
                )
            }

            composable(
                route = Routes.TEAM_DETAIL,
                arguments = listOf(navArgument("id") { type = NavType.IntType })
            ) { backStackEntry ->
                val id = backStackEntry.arguments!!.getInt("id")
                val imageLoader: ImageLoader = koinInject()
                val dialogHelper: DialogHelper = koinInject()
                val activity = LocalActivity.current as FragmentActivity
                val vm: TeamDetailViewModel = koinViewModel()
                val team by vm.teamInfo.collectAsStateWithLifecycle()
                val isLoading by vm.isLoading.collectAsStateWithLifecycle()
                val errorMessage by vm.errorMessage.collectAsStateWithLifecycle()
                LaunchedEffect(id) { vm.fetchTeamDetail(id) }
                TeamDetailScreen(
                    team = team,
                    isLoading = isLoading,
                    errorMessage = errorMessage,
                    onImageClick = { url ->
                        if (url != null && url != Constants.IMAGE_NOT_FOUND) {
                            dialogHelper.showImageDialog(activity, imageLoader, url)
                        }
                    },
                    onErrorConsumed = vm::clearErrorMessage
                )
            }

            composable(
                route = Routes.RACE_RESULT,
                arguments = listOf(
                    navArgument("id") { type = NavType.IntType },
                    navArgument("country") { type = NavType.StringType }
                )
            ) { backStackEntry ->
                val id = backStackEntry.arguments!!.getInt("id")
                val vm: RaceResultViewModel = koinViewModel()
                val results by vm.raceResult.collectAsStateWithLifecycle()
                val isLoading by vm.isLoading.collectAsStateWithLifecycle()
                val errorMessage by vm.errorMessage.collectAsStateWithLifecycle()
                LaunchedEffect(id) { vm.fetchRaceResult(id) }
                RaceResultScreen(
                    results = results,
                    isLoading = isLoading,
                    errorMessage = errorMessage,
                    onErrorConsumed = vm::clearErrorMessage
                )
            }

            composable(Routes.CIRCUITS) {
                val vm: CircuitsViewModel = koinViewModel()
                val circuits by vm.circuitsList.collectAsStateWithLifecycle()
                val isLoading by vm.isLoading.collectAsStateWithLifecycle()
                val errorMessage by vm.errorMessage.collectAsStateWithLifecycle()
                val imageLoader: ImageLoader = koinInject()
                val dialogHelper: DialogHelper = koinInject()
                val activity = LocalActivity.current as FragmentActivity
                CircuitsScreen(
                    circuits = circuits,
                    isLoading = isLoading,
                    errorMessage = errorMessage,
                    onMapClick = { url ->
                        if (url != Constants.IMAGE_NOT_FOUND) {
                            dialogHelper.showImageDialog(activity, imageLoader, url)
                        }
                    },
                    onErrorConsumed = vm::clearErrorMessage
                )
            }

            composable(Routes.FAVORITES) {
                val vm: FavoritesViewModel = koinViewModel()
                val races by vm.favoriteRaces.collectAsStateWithLifecycle()
                val isDeleted by vm.isDeleted.collectAsStateWithLifecycle()
                val errorMessage by vm.errorMessage.collectAsStateWithLifecycle()
                FavoritesScreen(
                    races = races,
                    isDeleted = isDeleted,
                    errorMessage = errorMessage,
                    onRemove = vm::deleteFavorite,
                    onNavigate = { id, country ->
                        navController.navigate("race_result/$id/${Uri.encode(country)}")
                    },
                    onDeletedConsumed = vm::clearIsDeleted,
                    onErrorConsumed = vm::clearErrorMessage
                )
            }

            composable(Routes.SETTINGS) {
                val vm: SettingsViewModel = koinViewModel()
                val imageLoader: ImageLoader = koinInject()
                val dialogHelper: DialogHelper = koinInject()
                val activity = LocalActivity.current as FragmentActivity
                val seasons by vm.seasonList.collectAsStateWithLifecycle()
                val currentSeasonSetting by sharedViewModel.selectedSeason.collectAsStateWithLifecycle()
                val themeMode by vm.themeMode.collectAsStateWithLifecycle()
                val isMusicPlaying by vm.isMusicPlaying.collectAsStateWithLifecycle()
                val errorMessage by vm.errorMessage.collectAsStateWithLifecycle()
                SettingsScreen(
                    seasons = seasons,
                    currentSeason = currentSeasonSetting,
                    themeMode = themeMode,
                    isMusicPlaying = isMusicPlaying,
                    errorMessage = errorMessage,
                    onThemeModeChange = vm::setThemeMode,
                    onSeasonSelected = { season -> sharedViewModel.updateSelectedSeason(season) },
                    onMusicToggle = vm::toggleMusic,
                    onAppIconClick = {
                        dialogHelper.showLocalImageDialog(
                            activity,
                            imageLoader,
                            R.drawable.appicon_alpha
                        )
                    },
                    onErrorConsumed = vm::clearErrorMessage
                )
            }
        }
    }
}

private data class BottomNavItem(val route: String, val iconRes: Int, val labelRes: Int)

@Composable
private fun F1BottomBar(selectedRoute: String, onTabSelected: (String) -> Unit) {
    val items = listOf(
        BottomNavItem(Routes.RACES, R.drawable.icon_race, R.string.nav_home),
        BottomNavItem(Routes.RANKING, R.drawable.icon_cup, R.string.nav_dashboard),
        BottomNavItem(Routes.CIRCUITS, R.drawable.icon_circuit, R.string.title_circuits),
        BottomNavItem(Routes.FAVORITES, R.drawable.icon_favorites_empty, R.string.title_favorites),
    )
    NavigationBar(containerColor = MaterialTheme.colorScheme.surface) {
        items.forEach { item ->
            val selected = selectedRoute == item.route
            NavigationBarItem(
                selected = selected,
                onClick = { onTabSelected(item.route) },
                icon = {
                    Icon(
                        painter = painterResource(item.iconRes),
                        contentDescription = stringResource(item.labelRes)
                    )
                },
                label = {
                    Text(
                        text = stringResource(item.labelRes),
                        style = MaterialTheme.typography.labelSmall
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.primary,
                    selectedTextColor = MaterialTheme.colorScheme.primary,
                    indicatorColor = Color.Transparent,
                    unselectedIconColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
                    unselectedTextColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                )
            )
        }
    }
}
