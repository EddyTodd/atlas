package com.ynmidk.atlas.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.ynmidk.atlas.core.fadeTopEdge
import com.ynmidk.atlas.theme.AtlasTextStyle
import com.ynmidk.atlas.theme.LocalAtlasComponents

@Composable
internal fun GameplaySettingsScreen(
    onBack: (() -> Unit)?
) = SettingsPlaceholderScreen(
    title = "Gameplay",
    description = "Gameplay settings will be available here soon.",
    onBack = onBack
)

@Composable
internal fun HowToPlayScreen(
    onBack: (() -> Unit)?
) = SettingsPlaceholderScreen(
    title = "How to Play",
    description = "How-to-play content will be available here soon.",
    onBack = onBack
)

@Composable
internal fun RemoveAdsScreen(
    onBack: (() -> Unit)?
) = SettingsPlaceholderScreen(
    title = "Remove Ads",
    description = "Purchase options and ad-free settings will be available here soon.",
    onBack = onBack
)

@Composable
internal fun MoreGamesScreen(
    onBack: (() -> Unit)?
) = SettingsPlaceholderScreen(
    title = "More Games",
    description = "Game recommendations and links will be available here soon.",
    onBack = onBack
)

@Composable
internal fun LanguageScreen(
    onBack: (() -> Unit)?
) = SettingsPlaceholderScreen(
    title = "Language",
    description = "Language options will be available here soon.",
    onBack = onBack
)

@Composable
private fun SettingsPlaceholderScreen(
    title: String,
    description: String,
    onBack: (() -> Unit)?
) {
    val c = LocalAtlasComponents.current
    Scaffold(
        containerColor = Color.Transparent,
        topBar = {
            c.TopBar(
                title = title,
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
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                c.Card {
                    c.Text("Coming Soon", AtlasTextStyle.CardTitle)
                    c.Text(description, AtlasTextStyle.Caption)
                }
            }
        }
    }
}
