package com.ynmidk.atlas.theme

sealed interface ThemeId {
    val label: String

    data object DefaultLight : ThemeId {
        override val label: String = "Light"
    }

    data object DefaultDark : ThemeId {
        override val label: String = "Dark"
    }

    data object HighContrastLight : ThemeId {
        override val label: String = "HC Light"
    }

    data object HighContrastDark : ThemeId {
        override val label: String = "HC Dark"
    }

    data object Forest : ThemeId {
        override val label: String = "Forest"
    }

    data object Ocean : ThemeId {
        override val label: String = "Ocean"
    }

    data object Desert : ThemeId {
        override val label: String = "Desert"
    }

    data object Twilight : ThemeId {
        override val label: String = "Twilight"
    }

    data object Sakura : ThemeId {
        override val label: String = "Sakura"
    }

    data object Monochrome : ThemeId {
        override val label: String = "Monochrome"
    }

    data object Sandstone : ThemeId {
        override val label: String = "Sandstone"
    }

    data object Retro : ThemeId {
        override val label: String = "Retro"
    }

    data object Neon : ThemeId {
        override val label: String = "Neon"
    }

    data object Blueprint : ThemeId {
        override val label: String = "Blueprint"
    }

    data object Aurora : ThemeId {
        override val label: String = "Aurora"
    }
}

private val themeIdToStorageKey: Map<ThemeId, String> = mapOf(
    ThemeId.DefaultLight to "default_light",
    ThemeId.DefaultDark to "default_dark",
    ThemeId.HighContrastLight to "hc_light",
    ThemeId.HighContrastDark to "hc_dark",
    ThemeId.Forest to "forest",
    ThemeId.Ocean to "ocean",
    ThemeId.Desert to "desert",
    ThemeId.Twilight to "twilight",
    ThemeId.Sakura to "sakura",
    ThemeId.Monochrome to "monochrome",
    ThemeId.Sandstone to "sandstone",
    ThemeId.Retro to "retro",
    ThemeId.Neon to "neon",
    ThemeId.Blueprint to "blueprint",
    ThemeId.Aurora to "aurora"
)

private val storageKeyToThemeId: Map<String, ThemeId> =
    themeIdToStorageKey.entries.associate { (themeId, key) -> key to themeId }

fun ThemeId.storageKey(): String = themeIdToStorageKey.getValue(this)

fun themeIdFromStorageKeyOrNull(value: String?): ThemeId? =
    value?.let(storageKeyToThemeId::get)

fun themeIdFromStorageKey(value: String?): ThemeId =
    themeIdFromStorageKeyOrNull(value) ?: ThemeId.DefaultLight
