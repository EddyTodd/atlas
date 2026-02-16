package com.ynmidk.atlas.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import com.ynmidk.atlas.core.BaseAtlasComponents
import com.ynmidk.atlas.core.BaseComponents
import com.ynmidk.atlas.core.ThemeDefinition

@Composable
fun AtlasTheme(
    definition: ThemeDefinition,
    navigation: AtlasNavigation = AtlasNavigation(),
    content: @Composable () -> Unit
) {
    val components = if (definition.components !is BaseAtlasComponents) {
        BaseComponents
    } else {
        definition.components
    }

    CompositionLocalProvider(
        LocalAtlasComponents provides components,
        LocalAtlasScreens provides definition.screens,
        LocalAtlasTypography provides definition.typography,
        LocalColors provides definition.colors,
        LocalAtlasNavigation provides navigation,
        content = content
    )
}
