package com.ynmidk.atlas.theme

enum class ThemeMode {
    Light,
    Dark,
    System,
    Custom
}

private val themeModeToStorageKey: Map<ThemeMode, String> = mapOf(
    ThemeMode.Light to "light",
    ThemeMode.Dark to "dark",
    ThemeMode.System to "system",
    ThemeMode.Custom to "custom"
)

private val storageKeyToThemeMode: Map<String, ThemeMode> =
    themeModeToStorageKey.entries.associate { (mode, key) -> key to mode }

fun ThemeMode.storageKey(): String = themeModeToStorageKey.getValue(this)

fun themeModeFromStorageKeyOrNull(value: String?): ThemeMode? =
    value?.let(storageKeyToThemeMode::get)

data class ThemeSelection(
    val mode: ThemeMode = ThemeMode.Light,
    val customThemeId: ThemeId = ThemeId.Sandstone,
    val highContrastEnabled: Boolean = false
) {
    fun resolveThemeId(systemInDarkTheme: Boolean): ThemeId = when (mode) {
        ThemeMode.Light -> modeThemeId(ThemeMode.Light, systemInDarkTheme)
        ThemeMode.Dark -> modeThemeId(ThemeMode.Dark, systemInDarkTheme)
        ThemeMode.System -> modeThemeId(ThemeMode.System, systemInDarkTheme)
        ThemeMode.Custom -> customThemeId
    }

    fun modeThemeId(
        mode: ThemeMode,
        systemInDarkTheme: Boolean
    ): ThemeId {
        val useDark = when (mode) {
            ThemeMode.Light -> false
            ThemeMode.Dark -> true
            ThemeMode.System -> systemInDarkTheme
            ThemeMode.Custom -> false
        }

        return when {
            useDark && highContrastEnabled -> ThemeId.HighContrastDark
            useDark -> ThemeId.DefaultDark
            highContrastEnabled -> ThemeId.HighContrastLight
            else -> ThemeId.DefaultLight
        }
    }

    fun displayLabel(systemInDarkTheme: Boolean): String = when (mode) {
        ThemeMode.Light -> if (highContrastEnabled) {
            "Light (High Contrast)"
        } else {
            "Light"
        }

        ThemeMode.Dark -> if (highContrastEnabled) {
            "Dark (High Contrast)"
        } else {
            "Dark"
        }

        ThemeMode.System -> if (highContrastEnabled) {
            if (systemInDarkTheme) {
                "System (Dark, High Contrast)"
            } else {
                "System (Light, High Contrast)"
            }
        } else if (systemInDarkTheme) {
            "System (Dark)"
        } else {
            "System (Light)"
        }

        ThemeMode.Custom -> customThemeId.label
    }
}
