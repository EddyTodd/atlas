package com.ynmidk.atlas.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.relocation.bringIntoViewRequester
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.withFrameNanos
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.ynmidk.atlas.core.CardStyle
import com.ynmidk.atlas.core.atlasVerticalScroll
import com.ynmidk.atlas.theme.AtlasTextStyle
import com.ynmidk.atlas.theme.LocalAtlasComponents
import com.ynmidk.atlas.theme.LocalAtlasTypography
import com.ynmidk.atlas.theme.LocalColors

@Composable
internal fun DefaultAchievementsScreen(
    achievements: List<AchievementItem>,
    highlightAchievementId: String?,
    onHighlightConsumed: (() -> Unit)?,
    onBack: (() -> Unit)?
) {
    val c = LocalAtlasComponents.current
    val scrollState = rememberScrollState()
    val bringIntoViewRequesters = remember(achievements) {
        achievements.associate { it.id to BringIntoViewRequester() }
    }

    LaunchedEffect(highlightAchievementId, achievements) {
        val targetId = highlightAchievementId ?: return@LaunchedEffect
        val requester = bringIntoViewRequesters[targetId]
        if (requester != null) {
            withFrameNanos { }
            requester.bringIntoView()
        }
        onHighlightConsumed?.invoke()
    }

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
                    .atlasVerticalScroll(scrollState)
                    .padding(horizontal = 32.dp, vertical = 16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                achievements.forEach { achievement ->
                    AchievementCardItem(
                        achievement = achievement,
                        modifier = Modifier.bringIntoViewRequester(
                            bringIntoViewRequesters.getValue(achievement.id)
                        )
                    )
                }
            }
        }
    }
}

@Composable
private fun AchievementCardItem(
    achievement: AchievementItem,
    modifier: Modifier = Modifier
) {
    val c = LocalAtlasComponents.current
    val colors = LocalColors.current
    val typography = LocalAtlasTypography.current
    val progressGoal = achievement.progressGoal
    val progressCurrent = achievement.progressCurrent
    val shouldShowProgress = progressGoal != null && progressGoal > 1 && progressCurrent != null
    val clampedProgress = if (shouldShowProgress) {
        progressCurrent.coerceIn(0, progressGoal)
    } else {
        0
    }

    c.Card(
        modifier = modifier,
        style = if (achievement.unlocked) CardStyle.Active else CardStyle.Regular
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (achievement.iconRes != null) {
                    Icon(
                        painter = painterResource(achievement.iconRes),
                        contentDescription = null,
                        modifier = Modifier.size(28.dp),
                        tint = colors.text
                    )
                } else {
                    c.Text(achievement.icon, AtlasTextStyle.Body)
                }

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

                    shouldShowProgress -> {
                        c.Text("$clampedProgress/$progressGoal", AtlasTextStyle.Caption)
                    }

                    !achievement.status.isNullOrBlank() -> {
                        c.Text(achievement.status, AtlasTextStyle.Caption)
                    }
                }
            }

            if (shouldShowProgress) {
                LinearProgressIndicator(
                    progress = { clampedProgress.toFloat() / progressGoal.toFloat() },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}
