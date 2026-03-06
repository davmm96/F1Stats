package com.david.f1stats.ui.ranking.drivers.driverDetail

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
import com.david.f1stats.R
import com.david.f1stats.domain.model.DriverDetail

@Composable
fun DriverDetailScreen(
    driver: DriverDetail?,
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

                driver != null -> DriverDetailContent(
                    driver = driver,
                    onImageClick = onImageClick
                )
            }
        }
    }
}

@Composable
private fun DriverDetailContent(
    driver: DriverDetail,
    onImageClick: (String?) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 16.dp)
    ) {
        // Header
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = driver.name,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    maxLines = 2
                )
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = driver.number,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = driver.country,
                        fontSize = 18.sp,
                        modifier = Modifier.padding(start = 15.dp)
                    )
                }
            }
            AsyncImage(
                model = driver.image,
                contentDescription = stringResource(R.string.driverContentDescription),
                modifier = Modifier
                    .size(100.dp)
                    .padding(start = 10.dp)
                    .clickable { onImageClick(driver.image) },
                contentScale = ContentScale.Fit,
                error = painterResource(R.drawable.driver_detail)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // WC trophy row
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
                    text = driver.worldChampionships,
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

        // Stats 2×2 grid
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            StatItem(
                iconRes = R.drawable.winner,
                iconDesc = stringResource(R.string.icon_wins),
                value = driver.wins,
                label = stringResource(R.string.wins_label)
            )
            StatItem(
                iconRes = R.drawable.podium,
                iconDesc = stringResource(R.string.icon_podiums),
                value = driver.podiums,
                label = stringResource(R.string.podiums_label)
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            StatItem(
                iconRes = R.drawable.race,
                iconDesc = stringResource(R.string.icon_races),
                value = driver.gpEntered,
                label = stringResource(R.string.races_label)
            )
            StatItem(
                iconRes = R.drawable.icon_stats,
                iconDesc = stringResource(R.string.icon_points),
                value = driver.points,
                label = stringResource(R.string.total_points_label)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Current team
        Text(
            text = stringResource(R.string.driver_actual_team_label),
            fontSize = 14.sp
        )
        AsyncImage(
            model = driver.teamImage,
            contentDescription = stringResource(R.string.image_actual_team),
            modifier = Modifier
                .size(150.dp)
                .align(Alignment.CenterHorizontally)
                .padding(top = 8.dp)
                .clickable { onImageClick(driver.teamImage) },
            contentScale = ContentScale.Fit
        )

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
