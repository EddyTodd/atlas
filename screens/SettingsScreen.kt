package com.ynmidk.atlas.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.ynmidk.atlas.core.CardStyle
import com.ynmidk.atlas.core.IconRole
import com.ynmidk.atlas.core.atlasIcon
import com.ynmidk.atlas.core.fadeTopEdge
import com.ynmidk.atlas.theme.AtlasTextStyle
import com.ynmidk.atlas.theme.LocalAtlasComponents
import com.ynmidk.atlas.theme.LocalAtlasNavigation
import com.ynmidk.atlas.theme.LocalColors

@Composable
internal fun SettingsScreen() {
    val c = LocalAtlasComponents.current
    val colors = LocalColors.current
    val navigation = LocalAtlasNavigation.current

    val sections = listOf(
        SettingsSection(
            label = "Game",
            items = listOf(
                SettingsItem("gameplay", "🎮", "Gameplay"),
                SettingsItem("how_to_play", "❓", "How to Play"),
                SettingsItem("statistics", "📊", "Statistics"),
                SettingsItem("achievements", "🏆", "Achievements")
            )
        ),
        SettingsSection(
            label = "Preferences",
            items = listOf(
                SettingsItem("theme", "🎨", "Theme"),
                SettingsItem("language", "🌐", "Language")
            )
        ),
        SettingsSection(
            label = "Store",
            items = listOf(
                SettingsItem("remove_ads", "🚫", "Remove Ads"),
                SettingsItem("more_games", "🕹️", "More Games")
            )
        ),
        SettingsSection(
            label = "Info",
            items = listOf(
                SettingsItem("about", "ℹ️", "About")
            )
        )
    )

    Scaffold(
        containerColor = Color.Transparent,
        topBar = {
            c.TopBar(
                title = "Settings",
                onLeadingIconClick = navigation.onNavigateBack
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
                    .padding(32.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                sections.forEach { section ->
                    section.items.forEach { item ->
                        c.Card(
                            style = CardStyle.Tappable,
                            onClick = {
                                when (item.id) {
                                    "gameplay" -> navigation.onOpenGameplaySettings()
                                    "how_to_play" -> navigation.onOpenHowToPlay()
                                    "statistics" -> navigation.onOpenStatistics()
                                    "achievements" -> navigation.onOpenAchievements()
                                    "theme" -> navigation.onOpenTheme()
                                    "language" -> navigation.onOpenLanguage()
                                    "remove_ads" -> navigation.onOpenRemoveAds()
                                    "more_games" -> navigation.onOpenMoreGames()
                                    "about" -> navigation.onOpenAbout()
                                }
                            }
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(12.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                c.Text(item.icon, AtlasTextStyle.Body)
                                Column(
                                    modifier = Modifier.weight(1f),
                                    verticalArrangement = Arrangement.spacedBy(2.dp)
                                ) {
                                    c.Text(item.title, AtlasTextStyle.CardTitle)
                                }
                                Icon(
                                    imageVector = atlasIcon(IconRole.ChevronRight),
                                    contentDescription = null,
                                    tint = colors.textMuted,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
