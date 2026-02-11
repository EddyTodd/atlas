package com.ynmidk.atlas.core

import com.ynmidk.atlas.core.AtlasComponents
import com.ynmidk.atlas.core.ThemeDefinition
import com.ynmidk.atlas.screens.AtlasScreens
import com.ynmidk.atlas.screens.DefaultScreens
import com.ynmidk.atlas.theme.Colors

open class BaseTheme(
    colors: Colors,
    typography: AtlasTypography = BaseTypography,
    components: AtlasComponents = BaseComponents,
    screens: AtlasScreens = DefaultScreens
) : ThemeDefinition(
    colors = colors,
    typography = typography,
    components = components,
    screens = screens
)

fun atlasTheme(
    colors: Colors,
    typography: AtlasTypography = BaseTypography,
    components: AtlasComponents = BaseComponents,
    screens: AtlasScreens = DefaultScreens
): ThemeDefinition = object : BaseTheme(
    colors = colors,
    typography = typography,
    components = components,
    screens = screens
) {}
