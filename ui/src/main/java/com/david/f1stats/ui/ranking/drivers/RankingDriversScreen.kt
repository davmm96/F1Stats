package com.david.f1stats.ui.ranking.drivers

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
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
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.david.f1stats.domain.model.RankingDriver
import com.david.f1stats.ui.R
import com.david.f1stats.ui.preview.previewRankingDrivers
import com.david.f1stats.ui.theme.F1DarkBlue
import com.david.f1stats.ui.theme.F1DarkGrey
import com.david.f1stats.ui.theme.F1StatsTheme
import com.david.f1stats.utils.getColor

@Composable
fun RankingDriversScreen(
    drivers: List<RankingDriver>,
    isLoading: Boolean,
    errorMessage: String?,
    onDriverClick: (Int) -> Unit,
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
                contentPadding = PaddingValues(12.dp)
            ) {
                items(drivers, key = { it.idDriver }) { driver ->
                    RankingDriverItem(
                        driver = driver,
                        onClick = { onDriverClick(driver.idDriver) },
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
private fun RankingDriverItem(
    driver: RankingDriver,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val isFirst = driver.position == 1
    val cardColor = if (isFirst) F1DarkGrey else Color.White
    val textColor = if (isFirst) Color.White else Color.Black
    val subTextColor = if (isFirst) Color.White.copy(alpha = 0.7f) else Color(0xFF3C3C3C)
    val arrowRes = if (isFirst) R.drawable.arrow_right_white else R.drawable.arrow_right
    val nameFontSize = if (isFirst) 18.sp else 14.sp

    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable(role = Role.Button) { onClick() },
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = RoundedCornerShape(6.dp),
        colors = CardDefaults.cardColors(containerColor = cardColor)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = driver.position.toString(),
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = textColor,
                modifier = Modifier.width(28.dp)
            )

            Box(
                modifier = Modifier
                    .width(4.dp)
                    .size(4.dp, 40.dp)
                    .background(colorResource(getColor(driver.idTeam)))
            )

            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 10.dp)
            ) {
                Text(
                    text = driver.name,
                    fontSize = nameFontSize,
                    fontWeight = FontWeight.Bold,
                    color = textColor
                )
                Text(
                    text = driver.team,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Normal,
                    color = subTextColor,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }

            Text(
                text = driver.points,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF3C3C3C),
                modifier = Modifier
                    .background(Color(0xFFE3E3E3), RoundedCornerShape(20.dp))
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            )

            Icon(
                painter = painterResource(arrowRes),
                contentDescription = stringResource(R.string.card_arrow_content_description),
                modifier = Modifier
                    .padding(start = 8.dp)
                    .size(20.dp),
                tint = Color.Unspecified
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun RankingDriversScreenPreview() {
    F1StatsTheme {
        RankingDriversScreen(
            drivers = previewRankingDrivers,
            isLoading = false,
            errorMessage = null,
            onDriverClick = {},
            onErrorConsumed = {}
        )
    }
}
