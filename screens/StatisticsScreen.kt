package com.ynmidk.atlas.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.ynmidk.atlas.core.CardStyle
import com.ynmidk.atlas.theme.LocalAtlasComponents
import com.ynmidk.atlas.theme.AtlasTextStyle

@Composable
internal fun StatisticsScreen(
    stats: List<StatisticItem>,
    bestTimes: List<BestTimeItem>,
    onBack: (() -> Unit)?
) {
    val c = LocalAtlasComponents.current
    Scaffold(
        containerColor = Color.Transparent,
        topBar = {
            c.TopBar(
                title = "Statistics",
                onLeadingIconClick = onBack
            )
        }
    ) { innerPadding ->
        c.ScreenBackground(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(innerPadding)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                stats.forEach { stat ->
                    c.Card(style = if (stat.highlighted) CardStyle.Active else CardStyle.Regular) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            c.Text(stat.label, AtlasTextStyle.Caption)
                            c.Text(stat.value, AtlasTextStyle.CardTitle)
                        }
                    }
                }

                c.Text("Best Times", AtlasTextStyle.Subtitle)
                c.Card {
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        bestTimes.forEach { item ->
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                c.Text(item.difficulty, AtlasTextStyle.Body)
                                c.Text(item.time, AtlasTextStyle.CardTitle)
                            }
                        }
                    }
                }
            }
        }
    }
}
