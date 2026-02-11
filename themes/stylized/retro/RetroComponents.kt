package com.ynmidk.atlas.themes.stylized.retro

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.ynmidk.atlas.core.BaseAtlasComponents
import com.ynmidk.atlas.core.BaseTokens
import com.ynmidk.atlas.core.ButtonSize
import com.ynmidk.atlas.core.ButtonVariant
import com.ynmidk.atlas.core.CardStyle
import com.ynmidk.atlas.core.ComponentTokens
import com.ynmidk.atlas.core.IconButtonVariant
import com.ynmidk.atlas.core.IconRole
import com.ynmidk.atlas.core.atlasIcon
import com.ynmidk.atlas.core.primaryButtonTextPadding
import com.ynmidk.atlas.theme.AtlasTextStyle
import com.ynmidk.atlas.theme.LocalColors
import kotlin.math.round
import kotlin.math.roundToInt

object RetroThemeComponents : BaseAtlasComponents() {

    private val retroFontFamily = FontFamily.SansSerif

    @Composable
    @OptIn(ExperimentalMaterial3Api::class)
    override fun TopBar(
        title: String?,
        onLeadingIconClick: (() -> Unit)?,
        onTrailingIconClick: (() -> Unit)?
    ) {
        val colors = LocalColors.current

        TopAppBar(
            title = {
                if (title != null) {
                    Text(
                        text = title,
                        style = textStyleFor(AtlasTextStyle.Title),
                        color = colors.text
                    )
                }
            },
            navigationIcon = {
                if (onLeadingIconClick != null) {
                    RetroIconButton(
                        role = IconRole.Back,
                        onClick = onLeadingIconClick,
                        enabled = true
                    )
                }
            },
            actions = {
                if (onTrailingIconClick != null) {
                    RetroIconButton(
                        role = IconRole.Settings,
                        onClick = onTrailingIconClick,
                        enabled = true
                    )
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = colors.bg,
                titleContentColor = colors.text
            )
        )
    }

    @Composable
    override fun Text(text: String, style: AtlasTextStyle) {
        val colors = LocalColors.current
        val color = if (style == AtlasTextStyle.Muted || style == AtlasTextStyle.Caption || style == AtlasTextStyle.CardSubtitle || style == AtlasTextStyle.Overline) {
            colors.textMuted
        } else {
            colors.text
        }
        Text(
            text = text,
            style = textStyleFor(style),
            color = color
        )
    }

    @Composable
    override fun Button(
        variant: ButtonVariant,
        size: ButtonSize,
        enabled: Boolean,
        onClick: () -> Unit,
        label: String,
        modifier: Modifier
    ) {
        val colors = LocalColors.current
        val borderColor = when (variant) {
            ButtonVariant.Primary -> if (enabled) RetroTokens.Black else null
            ButtonVariant.Secondary -> null
            ButtonVariant.Outline -> if (enabled) colors.accent else null
            ButtonVariant.Text,
            ButtonVariant.TextMuted -> null
        }
        val bevelSize = when (variant) {
            ButtonVariant.Primary -> if (enabled) RetroTokens.PrimaryBevelSize else RetroTokens.BevelSize
            ButtonVariant.Secondary,
            ButtonVariant.Outline,
            ButtonVariant.Text,
            ButtonVariant.TextMuted -> RetroTokens.BevelSize
        }
        val labelStyle = buttonTextStyleFor(size)
        val baseColor =
            if (variant == ButtonVariant.Text || variant == ButtonVariant.TextMuted) colors.bg else colors.cardBg
        val textModifier = if (variant == ButtonVariant.Primary) {
            Modifier.padding(horizontal = primaryButtonTextPadding(size))
        } else {
            Modifier
        }

        RetroButtonSurface(
            enabled = enabled,
            onClick = onClick,
            modifier = modifier.height(buttonHeightFor(size)),
            baseColor = baseColor,
            borderColor = borderColor,
            disabledContentColor = RetroTokens.DarkShadow,
            bevelSize = bevelSize
        ) {
            Text(text = label, modifier = textModifier, style = labelStyle)
        }
    }

    @Composable
    override fun IconButton(
        variant: IconButtonVariant,
        size: ButtonSize,
        role: IconRole,
        enabled: Boolean,
        tintColor: Color?,
        onClick: () -> Unit
    ) {
        val colors = LocalColors.current
        val isPrimaryEnabled = enabled && variant == IconButtonVariant.Primary

        RetroButtonSurface(
            enabled = enabled,
            onClick = onClick,
            baseColor = colors.cardBg,
            modifier = Modifier.size(
                if (size == ButtonSize.Regular) {
                    ComponentTokens.ButtonRegularHeight
                } else {
                    ComponentTokens.ButtonCompactHeight
                }
            ),
            borderColor = if (isPrimaryEnabled) RetroTokens.Black else null,
            bevelSize = if (isPrimaryEnabled) RetroTokens.PrimaryBevelSize else RetroTokens.BevelSize,
            disabledContentColor = RetroTokens.DarkShadow
        ) {
            val icon = atlasIcon(role)
            Icon(imageVector = icon, contentDescription = role.javaClass.simpleName)
        }
    }

    @Composable
    private fun RetroIconButton(
        role: IconRole,
        onClick: () -> Unit,
        enabled: Boolean
    ) {
        IconButton(
            variant = IconButtonVariant.Primary,
            size = ButtonSize.Regular,
            role = role,
            enabled = enabled,
            onClick = onClick
        )
    }

    @Composable
    override fun Card(
        modifier: Modifier,
        style: CardStyle,
        enabled: Boolean,
        onClick: (() -> Unit)?,
        contentPadding: PaddingValues,
        content: @Composable () -> Unit
    ) {
        val colors = LocalColors.current

        if (style == CardStyle.Tappable) {
            val interactionSource = remember { MutableInteractionSource() }
            Box(
                modifier = modifier
                    .clickable(
                        enabled = enabled,
                        onClick = onClick ?: {},
                        interactionSource = interactionSource,
                        indication = null
                    )
                    .retroBevel(baseColor = colors.cardBg)
                    .border(BaseTokens.BorderWidth, colors.cardBg.tappableCardBorderColor())
                    .padding(contentPadding)
            ) {
                content()
            }
            return
        }

        val surfaceModifier = if (style == CardStyle.Active) {
            retroPrimarySurfaceModifier(colors.cardBg)
        } else {
            Modifier.retroBevel(baseColor = colors.cardBg)
        }

        Box(
            modifier = modifier
                .then(surfaceModifier)
                .padding(contentPadding)
        ) {
            content()
        }
    }

    @Composable
    override fun ScreenBackground(
        modifier: Modifier,
        content: @Composable () -> Unit
    ) {
        val colors = LocalColors.current
        Box(modifier = modifier.background(colors.bg)) {
            content()
        }
    }

    @Composable
    override fun Toggle(
        checked: Boolean,
        enabled: Boolean,
        onCheckedChange: (Boolean) -> Unit
    ) {
        val colors = LocalColors.current
        Switch(
            checked = checked,
            enabled = enabled,
            onCheckedChange = onCheckedChange,
            colors = SwitchDefaults.colors(
                checkedThumbColor = colors.bg,
                checkedTrackColor = colors.primary,
                uncheckedThumbColor = colors.btnText,
                uncheckedTrackColor = colors.dotInactive,
                disabledCheckedTrackColor = colors.primary.copy(alpha = RetroTokens.DisabledTrackAlpha),
                disabledCheckedThumbColor = colors.bg.copy(alpha = RetroTokens.DisabledThumbAlpha)
            )
        )
    }

    @Composable
    override fun Slider(
        value: Float,
        enabled: Boolean,
        onValueChange: (Float) -> Unit,
        valueRange: ClosedFloatingPointRange<Float>,
        steps: Int,
        onValueChangeFinished: (() -> Unit)?
    ) {
        RetroSlider(
            value = value,
            enabled = enabled,
            onValueChange = onValueChange,
            valueRange = valueRange,
            steps = steps,
            onValueChangeFinished = onValueChangeFinished
        )
    }

    @Composable
    private fun RetroSlider(
        value: Float,
        enabled: Boolean,
        onValueChange: (Float) -> Unit,
        valueRange: ClosedFloatingPointRange<Float>,
        steps: Int,
        onValueChangeFinished: (() -> Unit)?
    ) {
        val colors = LocalColors.current
        val range = (valueRange.endInclusive - valueRange.start).coerceAtLeast(0f)
        val thumbWidth = RetroTokens.SliderThumbWidth
        val thumbHeight = RetroTokens.SliderThumbHeight
        val trackHeight = RetroTokens.SliderTrackHeight
        val tickHeight = RetroTokens.SliderTickHeight

        BoxWithConstraints(
            modifier = Modifier
                .fillMaxWidth()
                .height(RetroTokens.SliderHeight)
        ) {
            val widthPx = constraints.maxWidth.toFloat()
            val thumbWidthPx = with(LocalDensity.current) { thumbWidth.toPx() }
            val usableWidth = (widthPx - thumbWidthPx).coerceAtLeast(1f)
            val fraction = if (range > 0f) {
                ((value - valueRange.start) / range).coerceIn(0f, 1f)
            } else {
                0f
            }
            val thumbX = fraction * usableWidth

            fun updateValue(positionX: Float) {
                if (!enabled) return
                val clamped = (positionX - thumbWidthPx / 2f).coerceIn(0f, usableWidth)
                var newValue = valueRange.start + (clamped / usableWidth) * range
                if (steps > 0 && range > 0f) {
                    val stepSize = range / (steps + 1)
                    newValue = (round((newValue - valueRange.start) / stepSize) * stepSize) + valueRange.start
                }
                onValueChange(newValue.coerceIn(valueRange.start, valueRange.endInclusive))
            }

            val interactionModifier = if (enabled) {
                Modifier
                    .pointerInput(valueRange, steps, true) {
                        detectTapGestures { offset ->
                            updateValue(offset.x)
                            onValueChangeFinished?.invoke()
                        }
                    }
                    .pointerInput(valueRange, steps, true) {
                        detectDragGestures(
                            onDragEnd = { onValueChangeFinished?.invoke() },
                            onDragCancel = { onValueChangeFinished?.invoke() }
                        ) { change, _ ->
                            updateValue(change.position.x)
                            change.consume()
                        }
                    }
            } else {
                Modifier
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(RetroTokens.SliderHeight)
                    .then(interactionModifier)
            ) {
                Canvas(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(RetroTokens.SliderCanvasHeight)
                        .align(Alignment.TopStart)
                ) {
                    val lineHeight = trackHeight.toPx()
                    val stroke = RetroTokens.SliderStrokeWidth.toPx()
                    val y = RetroTokens.SliderTrackY.toPx()
                    val top = y - lineHeight / 2f
                    val bottom = y + lineHeight / 2f

                    drawRect(
                        color = RetroTokens.Black,
                        topLeft = androidx.compose.ui.geometry.Offset(0f, top),
                        size = androidx.compose.ui.geometry.Size(size.width, lineHeight)
                    )

                    drawLine(
                        color = RetroTokens.DarkShadow,
                        start = androidx.compose.ui.geometry.Offset(0f, top),
                        end = androidx.compose.ui.geometry.Offset(size.width, top),
                        strokeWidth = stroke
                    )
                    drawLine(
                        color = RetroTokens.DarkShadow,
                        start = androidx.compose.ui.geometry.Offset(0f, top),
                        end = androidx.compose.ui.geometry.Offset(0f, bottom),
                        strokeWidth = stroke
                    )
                    drawLine(
                        color = RetroTokens.White,
                        start = androidx.compose.ui.geometry.Offset(0f, bottom),
                        end = androidx.compose.ui.geometry.Offset(size.width, bottom),
                        strokeWidth = stroke
                    )
                    drawLine(
                        color = RetroTokens.White,
                        start = androidx.compose.ui.geometry.Offset(size.width, top),
                        end = androidx.compose.ui.geometry.Offset(size.width, bottom),
                        strokeWidth = stroke
                    )

                    if (steps > 0) {
                        val tickCount = steps + 2
                        val tickY = bottom + RetroTokens.SliderTickOffset.toPx()
                        val tickHeightPx = tickHeight.toPx()
                        val tickStroke = RetroTokens.SliderStrokeWidth.toPx()
                        val thumbInset = thumbWidthPx / 2f
                        val tickSpan = (size.width - thumbWidthPx).coerceAtLeast(1f)
                        for (i in 0 until tickCount) {
                            val x = thumbInset + i * (tickSpan / (tickCount - 1))
                            drawLine(
                                color = RetroTokens.Black,
                                start = androidx.compose.ui.geometry.Offset(x, tickY),
                                end = androidx.compose.ui.geometry.Offset(x, tickY + tickHeightPx),
                                strokeWidth = tickStroke
                            )
                        }
                    }
                }

                Box(
                    modifier = Modifier
                        .offset { IntOffset(thumbX.roundToInt(), 0) }
                        .size(thumbWidth, thumbHeight)
                        .retroBevel(baseColor = colors.cardBg)
                )
            }
        }
    }

    @Composable
    override fun Divider() {
        val colors = LocalColors.current
        HorizontalDivider(color = colors.btnBorder, thickness = RetroTokens.DividerThickness)
    }

    @Composable
    override fun AlertDialog(
        title: String,
        text: String,
        onDismiss: () -> Unit,
        confirmLabel: String,
        dismissLabel: String?,
        onConfirm: (() -> Unit)?
    ) {
        val colors = LocalColors.current
        val confirmAction = onConfirm ?: onDismiss
        AlertDialog(
            onDismissRequest = onDismiss,
            confirmButton = {
                TextButton(onClick = confirmAction) {
                    Text(confirmLabel, color = colors.accent)
                }
            },
            dismissButton = if (dismissLabel != null) {
                { TextButton(onClick = onDismiss) { Text(dismissLabel, color = colors.text) } }
            } else {
                null
            },
            title = { Text(title, color = colors.text) },
            text = { Text(text, color = colors.textMuted) },
            containerColor = colors.secondaryCardBg
        )
    }

    @Composable
    private fun RetroButtonSurface(
        enabled: Boolean,
        onClick: () -> Unit,
        modifier: Modifier = Modifier,
        contentPadding: PaddingValues = ComponentTokens.ButtonContentPadding,
        baseColor: Color,
        borderColor: Color? = null,
        bevelSize: Dp = RetroTokens.BevelSize,
        disabledContentColor: Color = RetroTokens.DarkShadow,
        content: @Composable RowScope.() -> Unit
    ) {
        val interactionSource = remember { MutableInteractionSource() }
        val isPressed = interactionSource.collectIsPressedAsState().value

        val clickModifier = Modifier.clickable(
            enabled = enabled,
            onClick = onClick,
            interactionSource = interactionSource,
            indication = null
        )

        val borderModifier = if (borderColor != null) {
            Modifier.border(RetroTokens.ButtonBorderWidth, borderColor)
        } else {
            Modifier
        }

        Box(
            modifier = modifier
                .retroBevel(
                    raised = !isPressed,
                    baseColor = baseColor,
                    bevelSize = bevelSize
                )
                .then(borderModifier)
                .then(clickModifier)
                .padding(contentPadding),
            contentAlignment = Alignment.Center
        ) {
            if (!enabled) {
                CompositionLocalProvider(LocalContentColor provides RetroTokens.White) {
                    Row(
                        modifier = Modifier.offset(
                            RetroTokens.DisabledShadowOffset,
                            RetroTokens.DisabledShadowOffset
                        ),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        content()
                    }
                }
            }
            val contentColor = if (enabled) LocalColors.current.text else disabledContentColor
            CompositionLocalProvider(LocalContentColor provides contentColor) {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    content()
                }
            }
        }
    }

    private fun retroPrimarySurfaceModifier(baseColor: Color): Modifier =
        Modifier
            .retroBevel(
                baseColor = baseColor,
                bevelSize = RetroTokens.PrimaryBevelSize
            )
            .border(RetroTokens.ButtonBorderWidth, RetroTokens.Black)

    private fun textStyleFor(style: AtlasTextStyle): TextStyle =
        when (style) {
            AtlasTextStyle.DisplayTitle -> TextStyle(
                fontSize = ComponentTokens.DisplayTitleSize,
                fontWeight = FontWeight.Bold,
                fontFamily = retroFontFamily
            )

            AtlasTextStyle.Title -> TextStyle(
                fontSize = ComponentTokens.TitleSize,
                fontWeight = FontWeight.Bold,
                fontFamily = retroFontFamily
            )

            AtlasTextStyle.Subtitle,
            AtlasTextStyle.SectionTitle -> TextStyle(
                fontSize = ComponentTokens.SubtitleSize,
                fontWeight = FontWeight.Bold,
                fontFamily = retroFontFamily
            )

            AtlasTextStyle.CardTitle -> TextStyle(
                fontSize = ComponentTokens.CardTitleSize,
                fontWeight = FontWeight.Bold,
                fontFamily = retroFontFamily
            )

            AtlasTextStyle.CardSubtitle,
            AtlasTextStyle.Caption,
            AtlasTextStyle.Overline -> TextStyle(
                fontSize = ComponentTokens.CaptionSize,
                fontWeight = FontWeight.Normal,
                fontFamily = retroFontFamily
            )

            AtlasTextStyle.Body,
            AtlasTextStyle.Muted -> TextStyle(
                fontSize = ComponentTokens.BodySize,
                fontWeight = FontWeight.Normal,
                fontFamily = retroFontFamily
            )

            AtlasTextStyle.BodyStrong,
            AtlasTextStyle.Label -> TextStyle(
                fontSize = ComponentTokens.BodySize,
                fontWeight = FontWeight.Bold,
                fontFamily = retroFontFamily
            )
        }

    private fun buttonHeightFor(size: ButtonSize) = when (size) {
        ButtonSize.Regular -> ComponentTokens.ButtonRegularHeight
        ButtonSize.Compact -> ComponentTokens.ButtonCompactHeight
    }

    private fun buttonTextStyleFor(size: ButtonSize) = TextStyle(
        fontFamily = retroFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = when (size) {
            ButtonSize.Regular -> ComponentTokens.ButtonRegularLabelSize
            ButtonSize.Compact -> ComponentTokens.ButtonCompactLabelSize
        }
    )

    private fun Color.tappableCardBorderColor(): Color =
        if (luminance() > 0.5f) {
            Color.Black.copy(alpha = 0.05f)
        } else {
            Color.White.copy(alpha = 0.05f)
        }
}
