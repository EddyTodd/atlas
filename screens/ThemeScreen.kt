package com.ynmidk.atlas.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.GenericShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.ynmidk.atlas.core.AtlasThemeOptionCard
import com.ynmidk.atlas.core.CardStyle
import com.ynmidk.atlas.core.IconRole
import com.ynmidk.atlas.core.atlasIcon
import com.ynmidk.atlas.core.atlasVerticalScroll
import com.ynmidk.atlas.theme.AtlasTextStyle
import com.ynmidk.atlas.theme.LocalAtlasComponents
import com.ynmidk.atlas.theme.LocalAtlasScreens
import com.ynmidk.atlas.theme.LocalColors
import com.ynmidk.atlas.theme.ThemeId
import com.ynmidk.atlas.theme.ThemeMode
import com.ynmidk.atlas.theme.ThemeRegistry
import com.ynmidk.atlas.theme.ThemeSelection
import com.ynmidk.atlas.theme.storageKey
import com.ynmidk.atlas.theme.themeIdFromStorageKeyOrNull

private const val MODE_LIGHT_OPTION_ID = "mode_light"
private const val MODE_SYSTEM_OPTION_ID = "mode_system"
private const val MODE_DARK_OPTION_ID = "mode_dark"

@Composable
fun ThemeScreen(
    themeSelection: ThemeSelection,
    systemInDarkTheme: Boolean,
    onBack: (() -> Unit)? = null,
    onThemeSelectionChange: (ThemeSelection) -> Unit,
    preview: @Composable (themeId: ThemeId, onPreviewTap: () -> Unit) -> Unit
) {
    val screens = LocalAtlasScreens.current
    val availableThemes = ThemeRegistry.available()
    val lightModeThemeId = themeSelection.modeThemeId(
        mode = ThemeMode.Light,
        systemInDarkTheme = systemInDarkTheme
    )
    val darkModeThemeId = themeSelection.modeThemeId(
        mode = ThemeMode.Dark,
        systemInDarkTheme = systemInDarkTheme
    )

    val modeOptions = listOf(
        ThemeOption(
            id = MODE_LIGHT_OPTION_ID,
            name = ThemeId.DefaultLight.label,
            subtitle = "Mode",
            iconName = "sun",
            preview = {
                preview(lightModeThemeId) {
                    onThemeSelectionChange(themeSelection.copy(mode = ThemeMode.Light))
                }
            }
        ),
        ThemeOption(
            id = MODE_SYSTEM_OPTION_ID,
            name = "System",
            subtitle = "Mode",
            iconName = "system",
            preview = {
                DiagonalSystemPreview(
                    themePreview = preview,
                    lightThemeId = lightModeThemeId,
                    darkThemeId = darkModeThemeId
                ) {
                    onThemeSelectionChange(themeSelection.copy(mode = ThemeMode.System))
                }
            }
        ),
        ThemeOption(
            id = MODE_DARK_OPTION_ID,
            name = ThemeId.DefaultDark.label,
            subtitle = "Mode",
            iconName = "moon",
            preview = {
                preview(darkModeThemeId) {
                    onThemeSelectionChange(themeSelection.copy(mode = ThemeMode.Dark))
                }
            }
        )
    )

    val themedOptions = availableThemes.map { id ->
        ThemeOption(
            id = id.storageKey(),
            name = id.label,
            subtitle = themeCategory(id),
            iconName = themeIconName(id),
            preview = {
                preview(id) {
                    onThemeSelectionChange(
                        themeSelection.copy(
                            mode = ThemeMode.Custom,
                            customThemeId = id
                        )
                    )
                }
            }
        )
    }

    screens.ThemeScreen(
        options = modeOptions + themedOptions.filter { it.subtitle != "Mode" },
        selectedThemeId = selectedOptionId(themeSelection),
        highContrastEnabled = themeSelection.highContrastEnabled,
        onToggleHighContrast = { enabled ->
            onThemeSelectionChange(
                themeSelection.copy(highContrastEnabled = enabled)
            )
        },
        onSelectTheme = { option ->
            optionToThemeSelection(
                optionId = option.id,
                currentSelection = themeSelection,
                availableThemes = availableThemes
            )?.let(onThemeSelectionChange)
        },
        onBack = onBack
    )
}

@Composable
internal fun DefaultThemeScreen(
    options: List<ThemeOption>,
    selectedThemeId: String,
    highContrastEnabled: Boolean,
    onToggleHighContrast: (Boolean) -> Unit,
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
                    .padding(innerPadding)
                    .atlasVerticalScroll(rememberScrollState())
                    .padding(horizontal = 24.dp, vertical = 16.dp),
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
                                    iconName = option.iconName,
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

                    if (section == "Mode") {
                        HighContrastCard(
                            enabled = highContrastEnabled,
                            onToggle = onToggleHighContrast
                        )
                    }
                }
            }
        }
    }
}

private fun themeIconName(themeId: ThemeId): String = when (themeId) {
    ThemeId.DefaultLight -> "sun"
    ThemeId.DefaultDark -> "moon"
    ThemeId.HighContrastLight,
    ThemeId.HighContrastDark -> "high_contrast"

    ThemeId.Forest -> "forest"
    ThemeId.Ocean -> "water"
    ThemeId.Desert -> "mountain"
    ThemeId.Twilight -> "twilight"
    ThemeId.Sakura -> "flower"
    ThemeId.Monochrome -> "monotone"
    ThemeId.Sandstone -> "sunrise"
    ThemeId.Retro -> "retro"
    ThemeId.Neon -> "neon"
    ThemeId.Blueprint -> "blueprint"
    ThemeId.Aurora -> "sunrise"
}

private fun sectionOrder(keys: Set<String>): List<String> {
    val preferred = listOf("Mode", "Color", "Stylized")
    return preferred.filter { it in keys } + keys.filter { it !in preferred }.sorted()
}

@Composable
private fun DiagonalSystemPreview(
    themePreview: @Composable (themeId: ThemeId, onPreviewTap: () -> Unit) -> Unit,
    lightThemeId: ThemeId,
    darkThemeId: ThemeId,
    onPreviewTap: () -> Unit
) {
    val lightShape = remember {
        GenericShape { size, _ ->
            moveTo(0f, 0f)
            lineTo(size.width, 0f)
            lineTo(0f, size.height)
            close()
        }
    }
    val darkShape = remember {
        GenericShape { size, _ ->
            moveTo(size.width, 0f)
            lineTo(size.width, size.height)
            lineTo(0f, size.height)
            close()
        }
    }

    Box {
        Box(
            modifier = Modifier
                .clip(lightShape)
        ) {
            themePreview(lightThemeId, onPreviewTap)
        }
        Box(
            modifier = Modifier
                .matchParentSize()
                .clip(darkShape)
        ) {
            themePreview(darkThemeId, onPreviewTap)
        }
    }
}

@Composable
private fun HighContrastCard(
    enabled: Boolean,
    onToggle: (Boolean) -> Unit
) {
    val c = LocalAtlasComponents.current
    val colors = LocalColors.current

    c.Card(
        modifier = Modifier.fillMaxWidth(),
        style = if (enabled) CardStyle.Active else CardStyle.Tappable,
        onClick = { onToggle(!enabled) },
        contentPadding = PaddingValues(horizontal = 14.dp, vertical = 8.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = atlasIcon(IconRole.Settings),
                contentDescription = null,
                tint = colors.textMuted
            )
            c.Text("High Contrast", AtlasTextStyle.CardTitle)
            Spacer(modifier = Modifier.weight(1f))
            c.Toggle(
                checked = enabled,
                enabled = true,
                onCheckedChange = onToggle
            )
        }
    }
}

private fun selectedOptionId(selection: ThemeSelection): String = when (selection.mode) {
    ThemeMode.Light -> MODE_LIGHT_OPTION_ID
    ThemeMode.System -> MODE_SYSTEM_OPTION_ID
    ThemeMode.Dark -> MODE_DARK_OPTION_ID
    ThemeMode.Custom -> selection.customThemeId.storageKey()
}

private fun optionToThemeSelection(
    optionId: String,
    currentSelection: ThemeSelection,
    availableThemes: List<ThemeId>
): ThemeSelection? = when (optionId) {
    MODE_LIGHT_OPTION_ID -> currentSelection.copy(mode = ThemeMode.Light)
    MODE_SYSTEM_OPTION_ID -> currentSelection.copy(mode = ThemeMode.System)
    MODE_DARK_OPTION_ID -> currentSelection.copy(mode = ThemeMode.Dark)
    else -> themeIdFromStorageKeyOrNull(optionId)
        ?.takeIf { it in availableThemes && themeCategory(it) != "Mode" }
        ?.let { themeId ->
            currentSelection.copy(
                mode = ThemeMode.Custom,
                customThemeId = themeId
            )
        }
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
