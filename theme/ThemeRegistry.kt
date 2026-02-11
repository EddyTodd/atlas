package com.ynmidk.atlas.theme

import com.ynmidk.atlas.core.ThemeDefinition
import com.ynmidk.atlas.themes.ModeThemes
import com.ynmidk.atlas.themes.PaletteThemes
import com.ynmidk.atlas.themes.StylizedThemes

object ThemeRegistry {

    private val themes: Map<ThemeId, ThemeDefinition> = mapOf(
        ThemeId.DefaultLight to ModeThemes.Light,
        ThemeId.DefaultDark to ModeThemes.Dark,
        ThemeId.HighContrastLight to ModeThemes.HighContrastLight,
        ThemeId.HighContrastDark to ModeThemes.HighContrastDark,
        ThemeId.Forest to PaletteThemes.Forest,
        ThemeId.Ocean to PaletteThemes.Ocean,
        ThemeId.Desert to PaletteThemes.Desert,
        ThemeId.Twilight to PaletteThemes.Twilight,
        ThemeId.Sakura to PaletteThemes.Sakura,
        ThemeId.Monochrome to PaletteThemes.Monochrome,
        ThemeId.Sandstone to PaletteThemes.Sandstone,
        ThemeId.Retro to StylizedThemes.Retro,
        ThemeId.Neon to StylizedThemes.Neon,
        ThemeId.Blueprint to StylizedThemes.Blueprint,
        ThemeId.Aurora to StylizedThemes.Aurora
    )

    fun available(): List<ThemeId> = themes.keys.toList()

    fun get(id: ThemeId): ThemeDefinition =
        themes[id] ?: themes.getValue(ThemeId.DefaultLight)
}
