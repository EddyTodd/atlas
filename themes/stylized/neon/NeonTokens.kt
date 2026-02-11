package com.ynmidk.atlas.themes.stylized.neon

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ynmidk.atlas.themes.StylizedColors

object NeonTokens {
    val colors = StylizedColors.Neon

    val SurfaceStart = Color(0xF2161626)
    val SurfaceEnd = Color(0xF20A0A12)

    val BoardStart = Color(0xFF161626)
    val BoardMid = Color(0xFF10111C)
    val BoardEnd = Color(0xFF0A0D12)

    val SurfaceBrush: Brush = Brush.linearGradient(
        colors = listOf(SurfaceStart, SurfaceEnd)
    )

    val BoardBrush: Brush = Brush.linearGradient(
        colors = listOf(BoardStart, BoardMid, BoardEnd)
    )

    val Magenta = Color(0xFFFF00FF)
    val Cyan = Color(0xFF00FFFF)
    val White = Color(0xE6FFFFFF)
    val ScreenBackground = Color(0xFF0A0A0F)

    val ButtonBorderWidth = 2.dp
    val ButtonCornerRadius = 18.dp
    val ButtonGlowBlur = 8.dp
    const val ButtonGlowAlpha = 0.8f
    const val ButtonEnabledIntensity = 0.8f
    const val ButtonDisabledIntensity = 0.2f
    val ButtonAmbientBlur = 24.dp
    val ButtonAmbientAlpha = 0.25f
    val ButtonDisabledColor = Color(0xFF32323D)
    const val DisabledTrackAlpha = 0.35f
    const val DisabledThumbAlpha = 0.6f
    const val DisabledInactiveTrackAlpha = 0.25f

    val ButtonGlowWideBlur = 8.dp
    val ButtonGlowMidBlur = 2.dp
    const val DefaultGlowIntensity = 1.0f

    val ButtonLabelSize = 20.sp

    val DialogButtonSize = 18.sp
    val DialogTitleSize = 20.sp
    val DialogTextSize = 16.sp

    val DividerThickness = 1.dp
    const val DividerAlpha = 0.45f
    val CardCornerRadius = 12.dp
    val DialogCornerRadius = 28.dp
    val IconButtonSize = 56.dp
    val CardContentPadding = 16.dp

    val TabIndicatorStroke = 3.dp
    val TabLabelSize = 14.sp

    val PageTitleSize = 20.sp
    val SubtitleSize = 16.sp
    val BodySize = 14.sp

    const val CardActiveIntensity = 1.0f
    const val CardInactiveIntensity = 0.8f
    const val DialogIntensity = 0.8f

    const val TextGlowWideAlpha = 0.6f
    const val TextGlowMidAlpha = 0.9f
    const val TextGlowWideBlur = 45f
    const val TextGlowMidBlur = 20f
    const val TextGlowSharpBlur = 6f

    const val ScreenOverlayStart = 0f
    const val ScreenOverlayEnd = 0.75f
    const val ScreenOverlayOffsetStart = 0.1f
    const val ScreenOverlayOffsetEnd = 0.9f
    const val ScreenOverlayOffsetCenter = 0.5f
    val ScreenOverlayCyan = Color(0x1400FFFF)
    val ScreenOverlayMagenta = Color(0x14FF00FF)
    val ScreenOverlayGreen = Color(0x0A00FF88)

    const val BorderGlowWideMultiplier = 0.6f
    const val BorderGlowCoreMultiplier = 0.25f
    const val BorderGlowWideAlpha = 0.6f
    const val BorderGlowOuterAlpha = 0.4f
    const val BorderGlowMinStrokePx = 1f

    val GameTitleFontSize = 28.sp
    val HomeTitleFontSize = 36.sp
    val TitleGlowColor = Cyan
}
