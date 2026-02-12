package com.ynmidk.atlas.theme

import androidx.compose.runtime.staticCompositionLocalOf
import com.ynmidk.atlas.core.AtlasComponents
import com.ynmidk.atlas.core.AtlasTypography
import com.ynmidk.atlas.screens.AtlasScreens

val LocalAtlasComponents = staticCompositionLocalOf<AtlasComponents> {
    error("AtlasComponents not provided")
}

val LocalAtlasScreens = staticCompositionLocalOf<AtlasScreens> {
    error("AtlasScreens not provided")
}

val LocalAtlasTypography = staticCompositionLocalOf<AtlasTypography> {
    error("AtlasTypography not provided")
}

val LocalColors = staticCompositionLocalOf<Colors> {
    error("AtlasColors not provided")
}

val LocalAtlasNavigation = staticCompositionLocalOf<AtlasNavigation> {
    AtlasNavigation()
}
