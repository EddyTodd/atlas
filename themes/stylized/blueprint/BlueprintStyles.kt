package com.ynmidk.atlas.themes.stylized.blueprint

import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.clipRect
import androidx.compose.ui.text.TextMeasurer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.sp

internal val BlueprintFontFamily = FontFamily.Monospace

private val BlueprintGridBackground = Color(0xFF0A3050)
private val BlueprintGridMajorLine = Color(0xFF184A73)
private val BlueprintGridMinorLine = Color(0xFF123756)
private const val BlueprintGridStrokeWidth = 2f

@Composable
internal fun Modifier.blueprintGridBackground(): Modifier {
    val textMeasurer = rememberTextMeasurer()
    val textStyle = TextStyle(
        fontFamily = BlueprintFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 14.sp,
        color = BlueprintGridMajorLine
    )
    return background(BlueprintGridBackground).drawBehind {
        drawBlueprintGrid(textMeasurer, textStyle)
    }
}

private fun DrawScope.drawBlueprintGrid(
    textMeasurer: TextMeasurer,
    textStyle: TextStyle
) {
    val canvasWidth = size.width
    val canvasHeight = size.height
    val minorCellSize = canvasWidth / 25f
    val majorCellSize = canvasWidth / 5f

    for (i in 0..25) {
        if (i % 5 != 0) {
            val x = i * minorCellSize
            drawLine(
                color = BlueprintGridMinorLine,
                start = Offset(x, 0f),
                end = Offset(x, canvasHeight),
                strokeWidth = BlueprintGridStrokeWidth
            )
        }
    }

    var y = 0f
    var rowIndex = 0
    while (y <= canvasHeight) {
        if (rowIndex % 5 != 0) {
            drawLine(
                color = BlueprintGridMinorLine,
                start = Offset(0f, y),
                end = Offset(canvasWidth, y),
                strokeWidth = BlueprintGridStrokeWidth
            )
        }
        y += minorCellSize
        rowIndex++
    }

    val padding = 8f
    for (i in 1..4) {
        val x = i * majorCellSize
        drawLine(
            color = BlueprintGridMajorLine,
            start = Offset(x, 0f),
            end = Offset(x, canvasHeight),
            strokeWidth = BlueprintGridStrokeWidth
        )

        val labelText = (i * 100).toString()
        val textLayoutResult = textMeasurer.measure(labelText, textStyle)
        val textX = x - textLayoutResult.size.width - 4f
        drawText(
            textLayoutResult = textLayoutResult,
            topLeft = Offset(textX, padding)
        )
        drawText(
            textLayoutResult = textLayoutResult,
            topLeft = Offset(textX, canvasHeight - textLayoutResult.size.height - padding)
        )
    }

    y = 0f
    var lineNumber = 0
    while (y <= canvasHeight) {
        drawLine(
            color = BlueprintGridMajorLine,
            start = Offset(0f, y),
            end = Offset(canvasWidth, y),
            strokeWidth = BlueprintGridStrokeWidth
        )

        if (lineNumber > 0) {
            val labelText = (lineNumber * 100).toString()
            val textLayoutResult = textMeasurer.measure(labelText, textStyle)
            val textY = y - textLayoutResult.size.height - 4f
            if (textY > 0) {
                drawText(
                    textLayoutResult = textLayoutResult,
                    topLeft = Offset(padding, textY)
                )
                drawText(
                    textLayoutResult = textLayoutResult,
                    topLeft = Offset(canvasWidth - textLayoutResult.size.width - padding, textY)
                )
            }
        }

        y += majorCellSize
        lineNumber++
    }
}

private fun DrawScope.cardCornerRadiusPx(shape: Shape): Float {
    return when (val outline = shape.createOutline(size, layoutDirection, this)) {
        is Outline.Rounded -> minOf(
            outline.roundRect.topLeftCornerRadius.x,
            outline.roundRect.topLeftCornerRadius.y
        )

        else -> 0f
    }
}

internal fun Modifier.blueprintCardDecoration(
    lineColor: Color,
    dashEffect: PathEffect,
    shape: Shape
): Modifier = drawBehind {
    val strokeWidth = 2f
    val radius = cardCornerRadiusPx(shape)
    if (radius <= 0f) return@drawBehind

    clipRect(left = radius, top = 0f, right = radius * 2f, bottom = radius * 2f) {
        drawCircle(
            color = lineColor,
            radius = radius,
            center = Offset(radius, radius),
            style = Stroke(width = strokeWidth, pathEffect = dashEffect)
        )
    }
    clipRect(left = 0f, top = radius, right = radius * 2f, bottom = radius * 2f) {
        drawCircle(
            color = lineColor,
            radius = radius,
            center = Offset(radius, radius),
            style = Stroke(width = strokeWidth, pathEffect = dashEffect)
        )
    }

    clipRect(
        left = size.width - radius * 2f,
        top = 0f,
        right = size.width - radius,
        bottom = radius * 2f
    ) {
        drawCircle(
            color = lineColor,
            radius = radius,
            center = Offset(size.width - radius, radius),
            style = Stroke(width = strokeWidth, pathEffect = dashEffect)
        )
    }
    clipRect(
        left = size.width - radius * 2f,
        top = radius,
        right = size.width,
        bottom = radius * 2f
    ) {
        drawCircle(
            color = lineColor,
            radius = radius,
            center = Offset(size.width - radius, radius),
            style = Stroke(width = strokeWidth, pathEffect = dashEffect)
        )
    }

    clipRect(
        left = 0f,
        top = size.height - radius * 2f,
        right = radius,
        bottom = size.height - radius
    ) {
        drawCircle(
            color = lineColor,
            radius = radius,
            center = Offset(radius, size.height - radius),
            style = Stroke(width = strokeWidth, pathEffect = dashEffect)
        )
    }
    clipRect(
        left = radius,
        top = size.height - radius * 2f,
        right = radius * 2f,
        bottom = size.height
    ) {
        drawCircle(
            color = lineColor,
            radius = radius,
            center = Offset(radius, size.height - radius),
            style = Stroke(width = strokeWidth, pathEffect = dashEffect)
        )
    }

    clipRect(
        left = size.width - radius * 2f,
        top = size.height - radius,
        right = size.width,
        bottom = size.height
    ) {
        drawCircle(
            color = lineColor,
            radius = radius,
            center = Offset(size.width - radius, size.height - radius),
            style = Stroke(width = strokeWidth, pathEffect = dashEffect)
        )
    }
    clipRect(
        left = size.width - radius,
        top = size.height - radius * 2f,
        right = size.width,
        bottom = size.height - radius
    ) {
        drawCircle(
            color = lineColor,
            radius = radius,
            center = Offset(size.width - radius, size.height - radius),
            style = Stroke(width = strokeWidth, pathEffect = dashEffect)
        )
    }

    drawLine(
        color = lineColor,
        start = Offset(0f, radius),
        end = Offset(size.width, radius),
        strokeWidth = strokeWidth,
        pathEffect = dashEffect
    )
    drawLine(
        color = lineColor,
        start = Offset(0f, size.height - radius),
        end = Offset(size.width, size.height - radius),
        strokeWidth = strokeWidth,
        pathEffect = dashEffect
    )
    drawLine(
        color = lineColor,
        start = Offset(radius, 0f),
        end = Offset(radius, size.height),
        strokeWidth = strokeWidth,
        pathEffect = dashEffect
    )
    drawLine(
        color = lineColor,
        start = Offset(size.width - radius, 0f),
        end = Offset(size.width - radius, size.height),
        strokeWidth = strokeWidth,
        pathEffect = dashEffect
    )
}
