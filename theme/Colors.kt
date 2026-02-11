package com.ynmidk.atlas.theme

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color

@Immutable
data class Colors(
    val name: String,
    val primary: Color,
    val accent: Color,
    val bg: Color,
    val cardBg: Color,
    val secondaryCardBg: Color,
    val cellEmpty: Color,
    val text: Color,
    val textMuted: Color,
    val badge: Color,
    val badgeText: Color,
    val errorBg: Color,
    val errorFg: Color,
    val btnBorder: Color,
    val btnText: Color,
    val dotInactive: Color,
    val dotActive: Color
)
