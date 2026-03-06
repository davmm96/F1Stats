package com.david.f1stats.ui.ranking.teams.teamDetail

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.david.f1stats.domain.model.TeamDetail
import com.david.f1stats.ui.R

@Composable
fun TeamDetailScreen(
    team: TeamDetail?,
    isLoading: Boolean,
    errorMessage: String?,
    onImageClick: (String?) -> Unit,
    onErrorConsumed: () -> Unit,
    modifier: Modifier = Modifier
) {
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(errorMessage) {
        errorMessage?.let {
            snackbarHostState.showSnackbar(it)
            onErrorConsumed()
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        modifier = modifier
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when {
                isLoading -> CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center),
                    color = MaterialTheme.colorScheme.primary
                )

                team != null -> TeamDetailContent(
                    team = team,
                    onImageClick = onImageClick
                )
            }
        }
    }
}

@Composable
private fun TeamDetailContent(
    team: TeamDetail,
    onImageClick: (String?) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = team.name,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    maxLines = 2
                )
                Text(
                    text = team.location,
                    fontSize = 16.sp
                )
            }
            AsyncImage(
                model = team.image,
                contentDescription = stringResource(R.string.imageTeam),
                modifier = Modifier
                    .size(100.dp)
                    .padding(start = 10.dp)
                    .clickable { onImageClick(team.image) },
                contentScale = ContentScale.Fit
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(R.drawable.cup),
                contentDescription = stringResource(R.string.icon_wc),
                modifier = Modifier.size(80.dp)
            )
            Column(modifier = Modifier.padding(start = 20.dp)) {
                Text(
                    text = team.worldChampionships,
                    fontSize = 40.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = stringResource(R.string.world_championships_label),
                    fontSize = 11.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            StatItem(
                iconRes = R.drawable.winner,
                iconDesc = stringResource(R.string.icon_wins),
                value = team.wins,
                label = stringResource(R.string.wins_label)
            )
            StatItem(
                iconRes = R.drawable.driver,
                iconDesc = stringResource(R.string.icon_podiums),
                value = team.polePositions,
                label = stringResource(R.string.pole_positions_label)
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            StatItem(
                iconRes = R.drawable.time,
                iconDesc = stringResource(R.string.icon_fast_lap),
                value = team.fastestLaps,
                label = stringResource(R.string.fastest_laps_label)
            )
            StatItem(
                iconRes = R.drawable.race,
                iconDesc = stringResource(R.string.icon_first_season),
                value = team.firstSeason,
                label = stringResource(R.string.first_season_label)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
private fun StatItem(
    iconRes: Int,
    iconDesc: String,
    value: String,
    label: String
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Image(
            painter = painterResource(iconRes),
            contentDescription = iconDesc,
            modifier = Modifier.size(40.dp)
        )
        Column(modifier = Modifier.padding(start = 12.dp)) {
            Text(
                text = value,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = label,
                fontSize = 11.sp
            )
        }
    }
}
