package com.ynmidk.atlas.themes.stylized.retro

import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.unit.Dp
import kotlin.math.sqrt

internal fun Modifier.retroBevel(
    raised: Boolean = true,
    baseColor: Color,
    bevelSize: Dp = RetroTokens.BevelSize
): Modifier = drawBehind {
    val stroke = bevelSize.toPx()
    val width = size.width
    val height = size.height
    val topLeft = if (raised) RetroTokens.Highlight else RetroTokens.DarkShadow
    val bottomRight = if (raised) RetroTokens.DarkShadow else RetroTokens.Highlight

    drawRect(color = baseColor)
    val topPath = Path().apply {
        moveTo(0f, 0f)
        lineTo(width, 0f)
        lineTo(width - stroke, stroke)
        lineTo(stroke, stroke)
        close()
    }
    val leftPath = Path().apply {
        moveTo(0f, 0f)
        lineTo(stroke, stroke)
        lineTo(stroke, height - stroke)
        lineTo(0f, height)
        close()
    }
    val bottomPath = Path().apply {
        moveTo(stroke, height - stroke)
        lineTo(width - stroke, height - stroke)
        lineTo(width, height)
        lineTo(0f, height)
        close()
    }
    val rightPath = Path().apply {
        moveTo(width, 0f)
        lineTo(width, height)
        lineTo(width - stroke, height - stroke)
        lineTo(width - stroke, stroke)
        close()
    }

    drawPath(path = topPath, color = topLeft)
    drawPath(path = leftPath, color = topLeft)
    drawPath(path = bottomPath, color = bottomRight)
    drawPath(path = rightPath, color = bottomRight)
}

internal fun Modifier.retroTabBevel(
    raised: Boolean,
    baseColor: Color,
    showBottom: Boolean,
    bevelSize: Dp = RetroTokens.TabBevelSize,
    cornerCut: Dp = RetroTokens.TabCornerCut
): Modifier = drawBehind {
    val stroke = bevelSize.toPx()
    val cut = cornerCut.toPx()
    val w = size.width
    val h = size.height
    val diagInset = stroke / sqrt(2f)

    val topLeft = if (raised) RetroTokens.Highlight else RetroTokens.DarkShadow
    val bottomRight = if (raised) RetroTokens.DarkShadow else RetroTokens.Highlight

    drawRect(color = baseColor)

    val topPath = Path().apply {
        moveTo(cut, 0f)
        lineTo(w - cut, 0f)
        lineTo(w - cut - diagInset, stroke)
        lineTo(cut + diagInset, stroke)
        close()
    }

    val leftPath = Path().apply {
        moveTo(0f, cut)
        lineTo(cut, 0f)
        lineTo(cut + diagInset, stroke)
        lineTo(stroke, cut + diagInset)
        lineTo(stroke, h - stroke)
        lineTo(0f, h)
        close()
    }

    val rightPath = Path().apply {
        moveTo(w, cut)
        lineTo(w, h)
        lineTo(w - stroke, h - stroke)
        lineTo(w - stroke, cut + diagInset)
        lineTo(w - cut - diagInset, stroke)
        lineTo(w - cut, 0f)
        close()
    }

    val bottomPath = Path().apply {
        moveTo(stroke, h - stroke)
        lineTo(w - stroke, h - stroke)
        lineTo(w, h)
        lineTo(0f, h)
        close()
    }

    drawPath(path = topPath, color = topLeft)
    drawPath(path = leftPath, color = topLeft)
    if (showBottom) drawPath(path = bottomPath, color = bottomRight)
    drawPath(path = rightPath, color = bottomRight)
}
