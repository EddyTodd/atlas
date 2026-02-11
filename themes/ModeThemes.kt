package com.ynmidk.atlas.themes

import com.ynmidk.atlas.core.ThemeDefinition
import com.ynmidk.atlas.core.atlasTheme

object ModeThemes {
    val Light: ThemeDefinition = atlasTheme(colors = ModeColors.Light)
    val Dark: ThemeDefinition = atlasTheme(colors = ModeColors.Dark)
    val HighContrastLight: ThemeDefinition = atlasTheme(colors = ModeColors.HighContrastLight)
    val HighContrastDark: ThemeDefinition = atlasTheme(colors = ModeColors.HighContrastDark)
}
