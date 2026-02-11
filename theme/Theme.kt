package com.ynmidk.atlas.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import com.ynmidk.atlas.core.ThemeDefinition

@Composable
fun AtlasTheme(
    definition: ThemeDefinition,
    content: @Composable () -> Unit
) {
    CompositionLocalProvider(
        LocalAtlasComponents provides definition.components,
        LocalAtlasScreens provides definition.screens,
        LocalAtlasTypography provides definition.typography,
        LocalColors provides definition.colors,
        content = content
    )
}
