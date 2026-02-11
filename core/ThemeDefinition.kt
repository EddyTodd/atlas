package com.ynmidk.atlas.core

import com.ynmidk.atlas.screens.AtlasScreens
import com.ynmidk.atlas.core.AtlasTypography
import com.ynmidk.atlas.theme.Colors

open class ThemeDefinition(
    open val colors: Colors,
    open val typography: AtlasTypography,
    open val components: AtlasComponents,
    open val screens: AtlasScreens
)
