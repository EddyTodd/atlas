package com.ynmidk.atlas.themes.stylized.neon

import android.graphics.BlurMaskFilter
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.text.BasicText
import androidx.compose.material3.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.BlurredEdgeTreatment
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import android.graphics.Paint as AndroidPaint

@Composable
internal fun NeonGlowContent(
    glowColor: Color,
    glowIntensity: Float = NeonTokens.DefaultGlowIntensity,
    contentColor: Color = NeonTokens.White,
    content: @Composable () -> Unit
) {
    Box(
        modifier = Modifier.graphicsLayer(clip = false),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .blur(NeonTokens.ButtonGlowWideBlur, BlurredEdgeTreatment.Unbounded)
        ) {
            CompositionLocalProvider(LocalContentColor provides glowColor.copy(glowIntensity)) {
                content()
            }
        }
        Box(
            modifier = Modifier
                .fillMaxSize()
                .blur(NeonTokens.ButtonGlowMidBlur, BlurredEdgeTreatment.Unbounded)
        ) {
            CompositionLocalProvider(LocalContentColor provides glowColor) {
                content()
            }
        }
        CompositionLocalProvider(LocalContentColor provides contentColor) {
            content()
        }
    }
}

@Composable
internal fun NeonText(
    text: String,
    fontSize: TextUnit,
    glowColor: Color = NeonTokens.Cyan,
    textColor: Color = NeonTokens.White,
    fontWeight: FontWeight = FontWeight.Bold,
    fontFamily: FontFamily? = null,
    modifier: Modifier = Modifier,
    maxLines: Int = Int.MAX_VALUE,
    softWrap: Boolean = true,
    overflow: TextOverflow = TextOverflow.Clip,
    textAlign: TextAlign? = null
) {
    val resolvedTextAlign = textAlign ?: TextAlign.Start
    var layoutResult by remember { mutableStateOf<TextLayoutResult?>(null) }
    BasicText(
        text = text,
        maxLines = maxLines,
        softWrap = softWrap,
        overflow = overflow,
        onTextLayout = { layoutResult = it },
        style = TextStyle(
            fontSize = fontSize,
            fontWeight = fontWeight,
            fontFamily = fontFamily,
            textAlign = resolvedTextAlign,
            color = textColor
        ),
        modifier = modifier.drawWithCache {
            val currentLayout = layoutResult
            onDrawBehind {
                if (currentLayout != null) {
                    drawText(
                        textLayoutResult = currentLayout,
                        color = Color.Transparent,
                        shadow = Shadow(
                            color = glowColor.copy(alpha = NeonTokens.TextGlowWideAlpha),
                            blurRadius = NeonTokens.TextGlowWideBlur
                        )
                    )
                    drawText(
                        textLayoutResult = currentLayout,
                        color = Color.Transparent,
                        shadow = Shadow(
                            color = glowColor.copy(alpha = NeonTokens.TextGlowMidAlpha),
                            blurRadius = NeonTokens.TextGlowMidBlur
                        )
                    )
                    drawText(
                        textLayoutResult = currentLayout,
                        color = Color.Transparent,
                        shadow = Shadow(
                            color = glowColor,
                            blurRadius = NeonTokens.TextGlowSharpBlur
                        )
                    )
                }
            }
        }
    )
}

internal fun Modifier.neonScreenOverlay(): Modifier =
    background(NeonTokens.ScreenBackground).drawBehind {
        val centerTopLeft = Offset(
            size.width * NeonTokens.ScreenOverlayOffsetStart,
            size.height * NeonTokens.ScreenOverlayOffsetStart
        )
        val centerBottomRight = Offset(
            size.width * NeonTokens.ScreenOverlayOffsetEnd,
            size.height * NeonTokens.ScreenOverlayOffsetEnd
        )
        val centerMiddle = Offset(
            size.width * NeonTokens.ScreenOverlayOffsetCenter,
            size.height * NeonTokens.ScreenOverlayOffsetCenter
        )
        val baseRadius = size.minDimension

        drawRect(
            brush = Brush.radialGradient(
                colorStops = arrayOf(
                    NeonTokens.ScreenOverlayStart to NeonTokens.ScreenOverlayCyan,
                    NeonTokens.ScreenOverlayEnd to Color.Transparent
                ),
                center = centerTopLeft,
                radius = baseRadius
            )
        )
        drawRect(
            brush = Brush.radialGradient(
                colorStops = arrayOf(
                    NeonTokens.ScreenOverlayStart to NeonTokens.ScreenOverlayMagenta,
                    NeonTokens.ScreenOverlayEnd to Color.Transparent
                ),
                center = centerBottomRight,
                radius = baseRadius
            )
        )
        drawRect(
            brush = Brush.radialGradient(
                colorStops = arrayOf(
                    NeonTokens.ScreenOverlayStart to NeonTokens.ScreenOverlayGreen,
                    NeonTokens.ScreenOverlayEnd to Color.Transparent
                ),
                center = centerMiddle,
                radius = baseRadius
            )
        )
    }

internal fun Modifier.neonSurface(
    glowColor: Color,
    cornerRadius: Dp,
    intensity: Float,
    brush: Brush,
    borderWidth: Dp,
    borderGlowColor: Color?,
    glowBlur: Dp,
    glowAlpha: Float
): Modifier = graphicsLayer(clip = false).drawBehind {
    val radius = cornerRadius.toPx()

    drawRoundRect(
        brush = brush,
        cornerRadius = CornerRadius(radius, radius)
    )

    borderGlowColor?.let { glow ->
        val glowBlurPx = glowBlur.toPx()
        if (glowBlurPx > 0f) {
            drawIntoCanvas { canvas ->
                val paint = AndroidPaint().apply {
                    isAntiAlias = true
                    style = AndroidPaint.Style.STROKE
                }

                paint.strokeWidth = borderWidth.toPx() * 1.5f
                paint.color = glow.copy(alpha = glowAlpha * intensity).toArgb()
                paint.maskFilter = BlurMaskFilter(glowBlurPx, BlurMaskFilter.Blur.NORMAL)
                canvas.nativeCanvas.drawRoundRect(
                    0f,
                    0f,
                    size.width,
                    size.height,
                    radius,
                    radius,
                    paint
                )

                paint.strokeWidth = borderWidth.toPx() * 2f
                paint.color = glow.copy(alpha = intensity).toArgb()
                paint.maskFilter = BlurMaskFilter(glowBlurPx / 3f, BlurMaskFilter.Blur.NORMAL)
                canvas.nativeCanvas.drawRoundRect(
                    0f,
                    0f,
                    size.width,
                    size.height,
                    radius,
                    radius,
                    paint
                )

                paint.strokeWidth = borderWidth.toPx() * 0.5f
                paint.color = glow.copy(alpha = 0.4f * intensity).toArgb()
                paint.maskFilter = BlurMaskFilter(glowBlurPx * 2f, BlurMaskFilter.Blur.NORMAL)
                canvas.nativeCanvas.drawRoundRect(
                    0f,
                    0f,
                    size.width,
                    size.height,
                    radius,
                    radius,
                    paint
                )

                paint.maskFilter = null
            }
        }
    }

    if (borderWidth > 0.dp) {
        drawRoundRect(
            color = glowColor,
            cornerRadius = CornerRadius(radius, radius),
            style = Stroke(width = borderWidth.toPx(), cap = StrokeCap.Round)
        )
    }
}
