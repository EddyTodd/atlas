package com.ynmidk.atlas.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import com.ynmidk.atlas.core.AtlasThemeOptionCard
import com.ynmidk.atlas.theme.LocalAtlasComponents
import com.ynmidk.atlas.theme.LocalAtlasScreens
import com.ynmidk.atlas.theme.AtlasTextStyle
import com.ynmidk.atlas.theme.ThemeId
import com.ynmidk.atlas.theme.ThemeRegistry
import com.ynmidk.atlas.theme.storageKey
import com.ynmidk.atlas.theme.themeIdFromStorageKeyOrNull

private const val SYSTEM_OPTION_ID = "system"

@Composable
fun ThemeScreen(
    themeId: ThemeId,
    onBack: (() -> Unit)? = null,
    onThemeChange: (ThemeId) -> Unit,
    preview: @Composable (themeId: ThemeId, onThemeChange: (ThemeId) -> Unit) -> Unit
) {
    val screens = LocalAtlasScreens.current
    val availableThemes = ThemeRegistry.available()
    val modeOptions = listOf(
        ThemeOption(
            id = ThemeId.DefaultLight.storageKey(),
            name = ThemeId.DefaultLight.label,
            subtitle = "Mode",
            preview = { preview(ThemeId.DefaultLight, onThemeChange) }
        ),
        ThemeOption(
            id = SYSTEM_OPTION_ID,
            name = "System",
            subtitle = "Mode",
            preview = { preview(themeId, onThemeChange) }
        ),
        ThemeOption(
            id = ThemeId.DefaultDark.storageKey(),
            name = ThemeId.DefaultDark.label,
            subtitle = "Mode",
            preview = { preview(ThemeId.DefaultDark, onThemeChange) }
        ),
        ThemeOption(
            id = ThemeId.HighContrastLight.storageKey(),
            name = ThemeId.HighContrastLight.label,
            subtitle = "Mode",
            preview = { preview(ThemeId.HighContrastLight, onThemeChange) }
        ),
        ThemeOption(
            id = ThemeId.HighContrastDark.storageKey(),
            name = ThemeId.HighContrastDark.label,
            subtitle = "Mode",
            preview = { preview(ThemeId.HighContrastDark, onThemeChange) }
        )
    )

    val themedOptions = availableThemes.map { id ->
        ThemeOption(
            id = id.storageKey(),
            name = id.label,
            subtitle = themeCategory(id),
            preview = { preview(id, onThemeChange) }
        )
    }

    screens.ThemeScreen(
        options = modeOptions + themedOptions.filter { it.subtitle != "Mode" },
        selectedThemeId = themeId.storageKey(),
        onSelectTheme = { option ->
            optionToThemeId(
                optionId = option.id,
                currentThemeId = themeId,
                availableThemes = availableThemes
            )?.let(onThemeChange)
        },
        onBack = onBack
    )
}

@Composable
internal fun DefaultThemeScreen(
    options: List<ThemeOption>,
    selectedThemeId: String,
    onSelectTheme: (ThemeOption) -> Unit,
    onBack: (() -> Unit)?
) {
    val c = LocalAtlasComponents.current
    val grouped = options.groupBy { it.subtitle }

    Scaffold(
        containerColor = Color.Transparent,
        topBar = {
            c.TopBar(
                title = "Theme",
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
                sectionOrder(grouped.keys).forEach { section ->
                    val sectionItems = grouped[section].orEmpty()
                    if (sectionItems.isEmpty()) return@forEach

                    c.Text(section, AtlasTextStyle.Subtitle)

                    sectionItems.chunked(3).forEach { rowItems ->
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            rowItems.forEach { option ->
                                AtlasThemeOptionCard(
                                    name = option.name,
                                    preview = option.preview,
                                    isActive = option.id == selectedThemeId,
                                    onClick = { onSelectTheme(option) },
                                    modifier = Modifier.weight(1f)
                                )
                            }
                            repeat(3 - rowItems.size) {
                                Spacer(modifier = Modifier.weight(1f))
                            }
                        }
                    }
                }
            }
        }
    }
}

private fun sectionOrder(keys: Set<String>): List<String> {
    val preferred = listOf("Mode", "Color", "Stylized")
    return preferred.filter { it in keys } + keys.filter { it !in preferred }.sorted()
}

private fun optionToThemeId(
    optionId: String,
    currentThemeId: ThemeId,
    availableThemes: List<ThemeId>
): ThemeId? = when (optionId) {
    SYSTEM_OPTION_ID -> currentThemeId
    else -> themeIdFromStorageKeyOrNull(optionId)
        ?.takeIf { it in availableThemes }
}

private fun themeCategory(themeId: ThemeId): String = when (themeId) {
    ThemeId.DefaultLight,
    ThemeId.DefaultDark,
    ThemeId.HighContrastLight,
    ThemeId.HighContrastDark -> "Mode"

    ThemeId.Forest,
    ThemeId.Ocean,
    ThemeId.Desert,
    ThemeId.Twilight,
    ThemeId.Sakura,
    ThemeId.Monochrome,
    ThemeId.Sandstone -> "Color"

    ThemeId.Retro,
    ThemeId.Neon,
    ThemeId.Blueprint,
    ThemeId.Aurora -> "Stylized"
}
