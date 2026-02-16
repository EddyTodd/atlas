package com.ynmidk.atlas.themes

import androidx.compose.ui.graphics.Color
import com.ynmidk.atlas.theme.Colors

private fun colorsFromPalette(
    name: String,
    isDark: Boolean,
    background: Color,
    boardBackground: Color,
    islands: Color,
    mineBackground: Color,
    primary: Color,
    accent: Color? = null,
    onPrimary: Color? = null,
    onBackground: Color? = null,
    errorBg: Color? = null,
    errorFg: Color? = null
): Colors {
    val text = onBackground ?: primary
    val muted = accent ?: text.copy(alpha = if (isDark) 0.7f else 0.65f)
    val border = text.copy(alpha = if (isDark) 0.1f else 0.12f)
    return Colors(
        name = name,
        primary = primary,
        accent = muted,
        bg = background,
        cardBg = islands,
        secondaryCardBg = boardBackground,
        cellEmpty = boardBackground,
        text = text,
        textMuted = muted,
        badge = primary,
        badgeText = onPrimary ?: background,
        errorBg = errorBg ?: mineBackground,
        errorFg = errorFg ?: Color.White,
        btnBorder = border,
        btnText = text,
        dotInactive = border,
        dotActive = muted
    )
}

object ModeColors {
    val Light = Colors(
        name = "Light",
        primary = ModeColorTokens.Light.Primary,
        accent = ModeColorTokens.Light.Accent,
        bg = ModeColorTokens.Light.Background,
        cardBg = ModeColorTokens.Light.CardBackground,
        secondaryCardBg = ModeColorTokens.Light.SecondaryCardBackground,
        cellEmpty = ModeColorTokens.Light.CellEmpty,
        text = ModeColorTokens.Light.Text,
        textMuted = ModeColorTokens.Light.TextMuted,
        badge = ModeColorTokens.Light.Badge,
        badgeText = ModeColorTokens.Light.BadgeText,
        errorBg = ModeColorTokens.Light.ErrorBackground,
        errorFg = ModeColorTokens.Light.ErrorForeground,
        btnBorder = ModeColorTokens.Light.ButtonBorder,
        btnText = ModeColorTokens.Light.ButtonText,
        dotInactive = ModeColorTokens.Light.DotInactive,
        dotActive = ModeColorTokens.Light.DotActive
    )

    val Dark = Colors(
        name = "Dark",
        primary = ModeColorTokens.Dark.Primary,
        accent = ModeColorTokens.Dark.Accent,
        bg = ModeColorTokens.Dark.Background,
        cardBg = ModeColorTokens.Dark.CardBackground,
        secondaryCardBg = ModeColorTokens.Dark.SecondaryCardBackground,
        cellEmpty = ModeColorTokens.Dark.CellEmpty,
        text = ModeColorTokens.Dark.Text,
        textMuted = ModeColorTokens.Dark.TextMuted,
        badge = ModeColorTokens.Dark.Badge,
        badgeText = ModeColorTokens.Dark.BadgeText,
        errorBg = ModeColorTokens.Dark.ErrorBackground,
        errorFg = ModeColorTokens.Dark.ErrorForeground,
        btnBorder = ModeColorTokens.Dark.ButtonBorder,
        btnText = ModeColorTokens.Dark.ButtonText,
        dotInactive = ModeColorTokens.Dark.DotInactive,
        dotActive = ModeColorTokens.Dark.DotActive
    )

    val HighContrastLight = Light.copy(name = "HC Light")

    val HighContrastDark = Dark.copy(name = "HC Dark")
}

private object ModeColorTokens {
    object Light {
        val Primary = Color(0xFF1C2433)
        val Accent = Color(0xFF4A7FB5)
        val Background = Color(0xFFF4F6F9)
        val CardBackground = Color(0xFFE2E7EE)
        val SecondaryCardBackground = Color(0xFFECF0F4)
        val CellEmpty = Color(0xFFECF0F4)
        val Text = Color(0xFF1C2433)
        val TextMuted = Color(0xFF7888A0)
        val Badge = Color(0xFF1C2433)
        val BadgeText = Color(0xFFF4F6F9)
        val ErrorBackground = Color(0xFFCC5B48)
        val ErrorForeground = Color(0xFFFFFFFF)
        val ButtonBorder = Color(0x1F1C2433)
        val ButtonText = Color(0xFF1C2433)
        val DotInactive = Color(0x1F1C2433)
        val DotActive = Color(0xFF4A7FB5)
    }

    object Dark {
        val Primary = Color(0xFFE8E6E2)
        val Accent = Color(0xFF6BA3CC)
        val Background = Color(0xFF1E1E22)
        val CardBackground = Color(0xFF2A2A30)
        val SecondaryCardBackground = Color(0xFF252529)
        val CellEmpty = Color(0xFF252529)
        val Text = Color(0xFFE8E6E2)
        val TextMuted = Color(0xFF7A787A)
        val Badge = Color(0xFFE8E6E2)
        val BadgeText = Color(0xFF1E1E22)
        val ErrorBackground = Color(0xFFC4423A)
        val ErrorForeground = Color(0xFFFFFFFF)
        val ButtonBorder = Color(0x1AFFFFFF)
        val ButtonText = Color(0xFFE8E6E2)
        val DotInactive = Color(0x1FFFFFFF)
        val DotActive = Color(0xFF6BA3CC)
    }
}

internal object PaletteColors {
    val Forest = colorsFromPalette(
        name = "Forest",
        isDark = true,
        background = Color(0xFF1C2E1E),
        boardBackground = Color(0xFF1E301F),
        islands = Color(0xFF263A28),
        mineBackground = Color(0xFFB84040),
        primary = Color(0xFFD4E8D0),
        accent = Color(0xFF5AAAD0),
        onPrimary = Color(0xFF1C2E1E),
        onBackground = Color(0xFFD4E8D0)
    )

    val Ocean = colorsFromPalette(
        name = "Ocean",
        isDark = true,
        background = Color(0xFF101C2A),
        boardBackground = Color(0xFF141F2E),
        islands = Color(0xFF182838),
        mineBackground = Color(0xFFC03A3A),
        primary = Color(0xFFC8DEF0),
        accent = Color(0xFF50AAE8),
        onPrimary = Color(0xFF101C2A),
        onBackground = Color(0xFFC8DEF0)
    )

    val Desert = colorsFromPalette(
        name = "Desert",
        isDark = true,
        background = Color(0xFF3E2723),
        boardBackground = Color(0xFF4F322A),
        islands = Color(0xFF5D4037),
        mineBackground = Color(0xFF212121),
        primary = Color(0xFFFFCC80),
        accent = Color(0xFF5A80A0)
    )

    val Twilight = colorsFromPalette(
        name = "Twilight",
        isDark = true,
        background = Color(0xFF1A1A2E),
        boardBackground = Color(0xFF1A1F38),
        islands = Color(0xFF1B294C),
        mineBackground = Color(0xFFE94560),
        primary = Color(0xFFBB86FC),
        accent = Color(0xFF6090D0)
    )

    val Sakura = colorsFromPalette(
        name = "Sakura",
        isDark = false,
        background = Color(0xFFFDF6F8),
        boardBackground = Color(0xFFFAF0F3),
        islands = Color(0xFFF5E4EA),
        mineBackground = Color(0xFFD45D7A),
        primary = Color(0xFFC2185B),
        accent = Color(0xFF7A8EB0),
        onPrimary = Color(0xFFFDF6F8),
        onBackground = Color(0xFFC2185B)
    )

    val Monochrome = colorsFromPalette(
        name = "Monochrome",
        isDark = true,
        background = Color(0xFF121212),
        boardBackground = Color(0xFF1D1D1D),
        islands = Color(0xFF323232),
        mineBackground = Color(0xFFBDBDBD),
        primary = Color(0xFFFFFFFF),
        accent = Color(0xFFE0E0E0),
        errorFg = Color(0xFF000000)
    )

    val Sandstone = Colors(
        name = "Sandstone",
        primary = Color(0xFFC4523B),
        accent = Color(0xFFC4523B),
        bg = Color(0xFFF6F4F0),
        cardBg = Color(0xFFEFECE6),
        secondaryCardBg = Color(0xFFE8E4DD),
        cellEmpty = Color(0xFFD9D4CC),
        text = Color(0xFF1A1A1A),
        textMuted = Color(0xFF8A8580),
        badge = Color(0xFFC4523B),
        badgeText = Color(0xFFFFFFFF),
        errorBg = Color(0xFFC4523B),
        errorFg = Color(0xFFFFFFFF),
        btnBorder = Color(0x1F000000),
        btnText = Color(0xFF1A1A1A),
        dotInactive = Color(0x1F000000),
        dotActive = Color(0xFFC4523B)
    )
}

internal object StylizedColors {
    val Retro = colorsFromPalette(
        name = "Retro",
        isDark = false,
        background = Color(0xFFC0C0C0),
        boardBackground = Color(0xFFC0C0C0),
        islands = Color(0xFFC0C0C0),
        mineBackground = Color(0xFFC0C0C0),
        primary = Color(0xFF000000),
        accent = Color(0xFF000000),
        onPrimary = Color(0xFFC0C0C0),
        onBackground = Color(0xFF000000),
        errorBg = Color(0xFFE83412)
    )

    val Neon = colorsFromPalette(
        name = "Neon",
        isDark = true,
        background = Color(0xFF0A0A0F),
        boardBackground = Color(0xFF0F0A1A),
        islands = Color(0xFF161626),
        mineBackground = Color(0xFF0A0A12),
        primary = Color(0xFF66FFFF),
        accent = Color(0xFFFF00FF),
        onPrimary = Color(0xFF0A0A0F),
        onBackground = Color(0xFFFFFFFF),
        errorBg = Color(0xFFF472B6),
        errorFg = Color(0xFF0A0A0F)
    )

    val Blueprint = colorsFromPalette(
        name = "Blueprint",
        isDark = true,
        background = Color(0xFF0A3050),
        boardBackground = Color(0xFF0A3050),
        islands = Color(0xFF1B4A6E),
        mineBackground = Color(0xFF0D47A1),
        primary = Color(0xFFEAF6FF),
        accent = Color(0xFFB3E5FC),
        onPrimary = Color(0xFF0A3050),
        onBackground = Color(0xFFFFFFFF),
        errorFg = Color(0xFF0A3050)
    )

    val Aurora = colorsFromPalette(
        name = "Aurora",
        isDark = true,
        background = Color(0xFF0A1022),
        boardBackground = Color(0xFF101A2E),
        islands = Color(0xFF15233C),
        mineBackground = Color(0xFF471B6A),
        primary = Color(0xFF8CF6E6),
        accent = Color(0xFFFF89C7),
        onPrimary = Color(0xFF0A1022),
        onBackground = Color(0xFFEAF8FF),
        errorBg = Color(0xFFB83C7A),
        errorFg = Color(0xFF0A1022)
    )
}
