package com.ynmidk.atlas.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.ynmidk.atlas.core.CardStyle
import com.ynmidk.atlas.core.fadeTopEdge
import com.ynmidk.atlas.theme.AtlasTextStyle
import com.ynmidk.atlas.theme.LocalAtlasComponents
import com.ynmidk.atlas.theme.LocalAtlasTypography
import com.ynmidk.atlas.theme.LocalColors

@Composable
internal fun DefaultAchievementsScreen(
    achievements: List<AchievementItem>,
    onBack: (() -> Unit)?
) {
    val c = LocalAtlasComponents.current
    Scaffold(
        containerColor = Color.Transparent,
        topBar = {
            c.TopBar(
                title = "Achievements",
                onLeadingIconClick = onBack
            )
        }
    ) { innerPadding ->
        c.ScreenBackground(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .fadeTopEdge()
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 32.dp, vertical = 16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                achievements.forEach { achievement ->
                    AchievementCardItem(achievement)
                }
            }
        }
    }
}

@Composable
private fun AchievementCardItem(
    achievement: AchievementItem
) {
    val c = LocalAtlasComponents.current
    val colors = LocalColors.current
    val typography = LocalAtlasTypography.current

    c.Card(
        style = if (achievement.unlocked) CardStyle.Active else CardStyle.Regular
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            c.Text(achievement.icon, AtlasTextStyle.Body)

            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                c.Text(achievement.name, AtlasTextStyle.CardTitle)
                c.Text(achievement.description, AtlasTextStyle.Caption)
            }

            when {
                achievement.unlocked -> {
                    Text(
                        text = "✓",
                        color = colors.primary,
                        style = typography.textStyle(AtlasTextStyle.CardTitle)
                    )
                }

                !achievement.status.isNullOrBlank() -> {
                    c.Text(achievement.status, AtlasTextStyle.Caption)
                }
            }
        }
    }
}

