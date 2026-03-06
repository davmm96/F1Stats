package com.david.f1stats.ui.ranking.raceResult.raceResultDetail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.david.f1stats.R
import com.david.f1stats.domain.model.RaceResult
import com.david.f1stats.ui.theme.F1DarkBlue
import com.david.f1stats.utils.getColor

private val Gold = Color(0xFFFFD700)
private val Silver = Color(0xFFB8B8B8)
private val Bronze = Color(0xFFCD7F32)

private fun positionBadgeColor(position: String): Color = when (position) {
    "1" -> Gold
    "2" -> Silver
    "3" -> Bronze
    else -> {
        val p = position.toIntOrNull() ?: return Color(0xFF9E9E9E)
        if (p <= 10) Color(0xFF444444) else Color(0xFF9E9E9E)
    }
}

private fun positionTextColor(position: String): Color = when (position) {
    "1", "2", "3" -> Color(0xFF1A1A1A)
    else -> Color.White
}

/** True if the driver did not finish (time is not a lap time / gap). */
private fun isDnf(result: RaceResult): Boolean {
    val t = result.time
    if (t == "-") return false          // winner with no time yet
    if (t.startsWith("+")) return false // normal gap
    if (t.contains(":")) return false   // winner's race time "1:30:26.123"
    if (t.contains("Lap")) return false // lapped cars "+X Lap(s)"
    return t.isNotBlank()
}

@Composable
fun RaceResultScreen(
    results: List<RaceResult>,
    isLoading: Boolean,
    errorMessage: String?,
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
            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center),
                    color = MaterialTheme.colorScheme.primary
                )
            } else {
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    item { RaceResultHeader() }
                    items(results) { result ->
                        RaceResultRow(result = result)
                        HorizontalDivider(color = Color(0xFFEEEEEE), thickness = 0.5.dp)
                    }
                }
            }
        }
    }
}

@Composable
private fun RaceResultHeader() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(F1DarkBlue)
            .padding(horizontal = 16.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(R.string.pos_label),
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            textAlign = TextAlign.Center,
            modifier = Modifier.weight(1.2f)
        )
        Text(
            text = stringResource(R.string.driver_label),
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            modifier = Modifier.weight(1.2f)
        )
        Text(
            text = stringResource(R.string.time_label),
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            modifier = Modifier.weight(2f)
        )
        Text(
            text = stringResource(R.string.points_label),
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            textAlign = TextAlign.Center,
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
private fun RaceResultRow(result: RaceResult) {
    val dnf = isDnf(result)
    val rowAlpha = if (dnf) 0.45f else 1f
    val badgeColor = positionBadgeColor(result.position)
    val badgeTextColor = positionTextColor(result.position)

    val dnfRowBg = MaterialTheme.colorScheme.errorContainer.copy(alpha = 0.25f)
    val dnfChipBg = MaterialTheme.colorScheme.errorContainer
    val dnfTextColor = MaterialTheme.colorScheme.onErrorContainer

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(if (dnf) dnfRowBg else Color.Transparent)
            .padding(horizontal = 16.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .weight(1.2f),
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .size(32.dp)
                    .background(badgeColor.copy(alpha = rowAlpha), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = result.position,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = badgeTextColor.copy(alpha = rowAlpha)
                )
            }
        }

        Row(
            modifier = Modifier.weight(1.2f),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(4.dp, 20.dp)
                    .background(colorResource(getColor(result.idTeam)).copy(alpha = rowAlpha))
            )
            Text(
                text = result.driverAbbr,
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = rowAlpha),
                modifier = Modifier.padding(start = 6.dp)
            )
        }

        Text(
            text = result.time,
            fontSize = 11.sp,
            fontWeight = FontWeight.Normal,
            color = (if (dnf) dnfTextColor else Color(0xFF3C3C3C)).copy(alpha = rowAlpha),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .weight(2f)
                .background(
                    if (dnf) dnfChipBg else Color(0xFFE3E3E3),
                    RoundedCornerShape(20.dp)
                )
                .padding(horizontal = 8.dp, vertical = 4.dp)
        )

        Text(
            text = result.points,
            fontSize = 13.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = rowAlpha),
            textAlign = TextAlign.Center,
            modifier = Modifier.weight(1f)
        )
    }
}
