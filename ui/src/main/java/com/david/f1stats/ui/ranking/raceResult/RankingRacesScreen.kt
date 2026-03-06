package com.david.f1stats.ui.ranking.raceResult

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.david.f1stats.ui.R
import com.david.f1stats.domain.model.Race
import com.david.f1stats.ui.theme.F1DarkBlue

@Composable
fun RankingRacesScreen(
    races: List<Race>,
    favoriteRacesIds: List<Int>,
    isLoading: Boolean,
    errorMessage: String?,
    favoriteMessage: Boolean?,
    onFavClick: (Race) -> Unit,
    onRaceClick: (Int, String) -> Unit,
    onErrorConsumed: () -> Unit,
    onFavoriteMessageConsumed: () -> Unit,
    modifier: Modifier = Modifier
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val addedMessage = stringResource(R.string.favorite_added)
    val removedMessage = stringResource(R.string.favorite_removed)

    LaunchedEffect(errorMessage) {
        errorMessage?.let {
            snackbarHostState.showSnackbar(it)
            onErrorConsumed()
        }
    }

    LaunchedEffect(favoriteMessage) {
        favoriteMessage?.let { added ->
            snackbarHostState.showSnackbar(if (added) addedMessage else removedMessage)
            onFavoriteMessageConsumed()
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(F1DarkBlue)
    ) {
        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center),
                color = MaterialTheme.colorScheme.primary
            )
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(horizontal = 12.dp, vertical = 8.dp)
            ) {
                items(races, key = { it.idRace }) { race ->
                    RankingRaceItem(
                        race = race,
                        isFavorite = favoriteRacesIds.contains(race.idRace),
                        onFavClick = { onFavClick(race) },
                        onNavigateClick = { onRaceClick(race.idRace, race.country) },
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                }
            }
        }
        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}

@Composable
private fun RankingRaceItem(
    race: Race,
    isFavorite: Boolean,
    onFavClick: () -> Unit,
    onNavigateClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = RoundedCornerShape(6.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onFavClick) {
                Icon(
                    painter = painterResource(
                        if (isFavorite) R.drawable.icon_favorites else R.drawable.icon_favorites_off
                    ),
                    contentDescription = stringResource(R.string.add_to_favorite_list),
                    modifier = Modifier.size(24.dp),
                    tint = Color.Unspecified
                )
            }

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = race.country,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = race.competition,
                    style = MaterialTheme.typography.bodyMedium,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Normal
                )
                Text(
                    text = race.laps,
                    style = MaterialTheme.typography.bodyMedium,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Normal
                )
            }

            IconButton(onClick = onNavigateClick) {
                Icon(
                    painter = painterResource(R.drawable.arrow_right),
                    contentDescription = stringResource(R.string.card_arrow_content_description),
                    modifier = Modifier.size(20.dp),
                    tint = Color.Unspecified
                )
            }
        }
    }
}
