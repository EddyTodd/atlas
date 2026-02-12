package com.ynmidk.atlas.themes.stylized.blueprint

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.ynmidk.atlas.core.BaseAtlasComponents
import com.ynmidk.atlas.core.ButtonSize
import com.ynmidk.atlas.core.ButtonVariant
import com.ynmidk.atlas.core.CardStyle
import com.ynmidk.atlas.core.ComponentTokens
import com.ynmidk.atlas.core.IconButtonVariant
import com.ynmidk.atlas.core.IconRole
import com.ynmidk.atlas.core.primaryButtonTextPadding
import com.ynmidk.atlas.theme.AtlasTextStyle
import com.ynmidk.atlas.theme.LocalColors
import java.util.Locale

object BlueprintThemeComponents : BaseAtlasComponents() {

    private val cardShape = RoundedCornerShape(BlueprintTokens.CardCornerRadius)

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
                        fontSize = BlueprintTokens.TopBarTitleSize,
                        fontWeight = FontWeight.Bold,
                        fontFamily = BlueprintFontFamily,
                        color = colors.text
                    )
                }
            },
            navigationIcon = {
                if (onLeadingIconClick != null) {
                    super.IconButton(
                        variant = IconButtonVariant.Primary,
                        size = ButtonSize.Regular,
                        role = IconRole.Back,
                        enabled = true,
                        tintColor = null,
                        onClick = onLeadingIconClick
                    )
                }
            },
            actions = {
                if (onTrailingIconClick != null) {
                    super.IconButton(
                        variant = IconButtonVariant.Primary,
                        size = ButtonSize.Regular,
                        role = IconRole.Settings,
                        enabled = true,
                        tintColor = null,
                        onClick = onTrailingIconClick
                    )
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = colors.bg,
                titleContentColor = colors.text
            ),
            modifier = Modifier.blueprintGridBackground()
        )
    }

    @Composable
    override fun Text(text: String, style: AtlasTextStyle) {
        val resolvedText = if (style == AtlasTextStyle.Subtitle) {
            text.uppercase(Locale.getDefault())
        } else {
            text
        }
        val colors = LocalColors.current
        val color =
            if (style == AtlasTextStyle.Muted || style == AtlasTextStyle.Caption || style == AtlasTextStyle.CardSubtitle || style == AtlasTextStyle.Overline) {
                colors.textMuted
            } else {
                colors.text
            }
        Text(
            text = resolvedText,
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
        when (variant) {
            ButtonVariant.Primary -> {
                BlueprintPrimaryButton(
                    text = label,
                    size = size,
                    enabled = enabled,
                    onClick = onClick,
                    modifier = modifier
                )
            }

            ButtonVariant.Secondary -> {
                BlueprintSecondaryButton(
                    text = label,
                    size = size,
                    enabled = enabled,
                    onClick = onClick,
                    modifier = modifier
                )
            }

            ButtonVariant.Outline -> {
                val colors = LocalColors.current
                TextButton(
                    onClick = onClick,
                    enabled = enabled,
                    modifier = modifier.height(buttonHeightFor(size)),
                    contentPadding = ComponentTokens.ButtonContentPadding,
                    colors = ButtonDefaults.textButtonColors(
                        contentColor = colors.primary,
                        disabledContentColor = colors.text.copy(alpha = BlueprintTokens.DisabledSecondaryTextAlpha)
                    )
                ) {
                    Text(
                        text = label,
                        fontSize = buttonLabelSizeFor(size),
                        fontWeight = FontWeight.Bold,
                        fontFamily = BlueprintFontFamily,
                        textDecoration = TextDecoration.Underline
                    )
                }
            }

            ButtonVariant.Text,
            ButtonVariant.TextMuted -> {
                TextButton(
                    onClick = onClick,
                    enabled = enabled,
                    modifier = modifier.height(buttonHeightFor(size)),
                    contentPadding = ComponentTokens.ButtonContentPadding
                ) {
                    Text(
                        text = label,
                        fontSize = buttonLabelSizeFor(size),
                        fontWeight = FontWeight.Bold,
                        fontFamily = BlueprintFontFamily,
                        textDecoration = TextDecoration.Underline
                    )
                }
            }
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
        super.IconButton(
            variant = variant,
            size = size,
            role = role,
            enabled = enabled,
            tintColor = tintColor,
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
        val lineColor = colors.primary.copy(alpha = BlueprintTokens.LineAlpha)
        val dashEffect = PathEffect.dashPathEffect(
            floatArrayOf(BlueprintTokens.DashLength, BlueprintTokens.DashGap),
            0f
        )
        val underlineTextStyle = TextStyle(textDecoration = TextDecoration.Underline)

        val borderColor = when (style) {
            CardStyle.Active -> colors.primary
            CardStyle.Tappable -> colors.cardBg.tappableCardBorderColor()
            CardStyle.Regular -> colors.primary.copy(alpha = BlueprintTokens.BorderActiveAlpha)
        }

        if (style == CardStyle.Tappable) {
            val interactionSource = remember { MutableInteractionSource() }
            Card(
                onClick = onClick ?: {},
                enabled = enabled,
                interactionSource = interactionSource,
                modifier = modifier,
                shape = cardShape,
                colors = CardDefaults.cardColors(containerColor = colors.cardBg),
                elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
                border = BorderStroke(BlueprintTokens.CardBorderWidth, borderColor)
            ) {
                Box {
                    Box(
                        modifier = Modifier.blueprintCardDecoration(
                            lineColor,
                            dashEffect,
                            cardShape
                        )
                    )
                    CompositionLocalProvider(
                        androidx.compose.material3.LocalContentColor provides colors.text
                    ) {
                        ProvideTextStyle(value = underlineTextStyle) {
                            Box(modifier = Modifier.padding(contentPadding)) { content() }
                        }
                    }
                }
            }
            return
        }

        Card(
            modifier = modifier,
            shape = cardShape,
            colors = CardDefaults.cardColors(containerColor = colors.cardBg),
            elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
            border = BorderStroke(BlueprintTokens.CardBorderWidth, borderColor)
        ) {
            Box {
                Box(modifier = Modifier.blueprintCardDecoration(lineColor, dashEffect, cardShape))
                CompositionLocalProvider(
                    androidx.compose.material3.LocalContentColor provides colors.text
                ) {
                    ProvideTextStyle(value = underlineTextStyle) {
                        Box(modifier = Modifier.padding(contentPadding)) { content() }
                    }
                }
            }
        }
    }

    @Composable
    override fun ScreenBackground(
        modifier: Modifier,
        content: @Composable () -> Unit
    ) {
        Box(modifier = modifier.blueprintGridBackground()) {
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
                disabledCheckedTrackColor = colors.primary.copy(alpha = BlueprintTokens.DisabledTrackAlpha),
                disabledCheckedThumbColor = colors.bg.copy(alpha = BlueprintTokens.DisabledThumbAlpha)
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
        val colors = LocalColors.current
        androidx.compose.material3.Slider(
            value = value,
            onValueChange = onValueChange,
            enabled = enabled,
            valueRange = valueRange,
            steps = steps,
            onValueChangeFinished = onValueChangeFinished,
            colors = SliderDefaults.colors(
                thumbColor = colors.primary,
                activeTrackColor = colors.primary,
                activeTickColor = colors.bg,
                inactiveTrackColor = colors.dotInactive,
                inactiveTickColor = colors.dotInactive,
                disabledThumbColor = colors.primary.copy(alpha = BlueprintTokens.DisabledTrackAlpha),
                disabledActiveTrackColor = colors.primary.copy(alpha = BlueprintTokens.DisabledTrackAlpha),
                disabledActiveTickColor = colors.bg.copy(alpha = BlueprintTokens.DisabledThumbAlpha),
                disabledInactiveTrackColor = colors.dotInactive.copy(alpha = BlueprintTokens.DisabledTickAlpha),
                disabledInactiveTickColor = colors.dotInactive.copy(alpha = BlueprintTokens.DisabledTickAlpha)
            )
        )
    }

    @Composable
    override fun Divider() {
        val colors = LocalColors.current
        HorizontalDivider(
            color = colors.primary.copy(alpha = BlueprintTokens.DividerAlpha),
            thickness = BlueprintTokens.DividerThickness
        )
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
            containerColor = colors.cardBg
        )
    }

    @Composable
    private fun BlueprintPrimaryButton(
        text: String,
        size: ButtonSize,
        enabled: Boolean,
        onClick: () -> Unit,
        modifier: Modifier
    ) {
        val colors = LocalColors.current
        androidx.compose.material3.Button(
            onClick = onClick,
            enabled = enabled,
            modifier = modifier.height(buttonHeightFor(size)),
            contentPadding = ComponentTokens.ButtonContentPadding,
            colors = ButtonDefaults.buttonColors(
                containerColor = colors.primary,
                contentColor = colors.bg,
                disabledContainerColor = colors.primary.copy(alpha = BlueprintTokens.DisabledPrimaryAlpha),
                disabledContentColor = colors.bg.copy(alpha = BlueprintTokens.DisabledPrimaryAlpha)
            )
        ) {
            Text(
                text = text,
                modifier = Modifier.padding(horizontal = primaryButtonTextPadding(size)),
                fontSize = buttonLabelSizeFor(size),
                fontWeight = FontWeight.Bold,
                fontFamily = BlueprintFontFamily,
                textDecoration = TextDecoration.Underline
            )
        }
    }

    @Composable
    private fun BlueprintSecondaryButton(
        text: String,
        size: ButtonSize,
        enabled: Boolean,
        onClick: () -> Unit,
        modifier: Modifier
    ) {
        val colors = LocalColors.current
        val borderColor = colors.primary.copy(alpha = BlueprintTokens.BorderActiveAlpha)
        val disabledGray = colors.text.copy(alpha = BlueprintTokens.DisabledSecondaryTextAlpha)
        val shape = RoundedCornerShape(BlueprintTokens.SecondaryButtonCornerPercent)
        androidx.compose.material3.Button(
            onClick = onClick,
            enabled = enabled,
            modifier = modifier
                .widthIn(min = BlueprintTokens.SecondaryButtonMinWidth)
                .height(buttonHeightFor(size)),
            contentPadding = ComponentTokens.ButtonContentPadding,
            shape = shape,
            border = BorderStroke(
                BlueprintTokens.CardBorderWidth,
                if (enabled) borderColor else borderColor.copy(alpha = BlueprintTokens.DisabledBorderAlpha)
            ),
            colors = ButtonDefaults.buttonColors(
                containerColor = colors.cardBg,
                disabledContainerColor = colors.secondaryCardBg,
                disabledContentColor = disabledGray
            )
        ) {
            Text(
                text = text,
                fontSize = buttonLabelSizeFor(size),
                fontWeight = FontWeight.Bold,
                fontFamily = BlueprintFontFamily,
                color = if (enabled) colors.text else disabledGray,
                textDecoration = TextDecoration.Underline
            )
        }
    }

    private fun textStyleFor(style: AtlasTextStyle): TextStyle =
        when (style) {
            AtlasTextStyle.DisplayTitle -> TextStyle(
                fontSize = ComponentTokens.DisplayTitleSize,
                fontWeight = FontWeight.Bold,
                fontFamily = BlueprintFontFamily
            )

            AtlasTextStyle.Title -> TextStyle(
                fontSize = ComponentTokens.TitleSize,
                fontWeight = FontWeight.Bold,
                fontFamily = BlueprintFontFamily
            )

            AtlasTextStyle.Subtitle -> TextStyle(
                fontSize = ComponentTokens.SubtitleSize,
                fontWeight = FontWeight.Bold,
                fontFamily = BlueprintFontFamily
            )

            AtlasTextStyle.SectionTitle -> TextStyle(
                fontSize = ComponentTokens.SectionTitleSize,
                fontWeight = FontWeight.Medium,
                fontFamily = BlueprintFontFamily
            )

            AtlasTextStyle.CardTitle,
            AtlasTextStyle.Label -> TextStyle(
                fontSize = ComponentTokens.CardTitleSize,
                fontWeight = FontWeight.Bold,
                fontFamily = BlueprintFontFamily
            )

            AtlasTextStyle.Body,
            AtlasTextStyle.BodyStrong -> TextStyle(
                fontSize = ComponentTokens.BodySize,
                fontWeight = FontWeight.Medium,
                fontFamily = BlueprintFontFamily
            )

            AtlasTextStyle.Muted -> TextStyle(
                fontSize = ComponentTokens.MutedSize,
                fontWeight = FontWeight.Medium,
                fontFamily = BlueprintFontFamily
            )

            AtlasTextStyle.CardSubtitle,
            AtlasTextStyle.Caption,
            AtlasTextStyle.Overline -> TextStyle(
                fontSize = ComponentTokens.CaptionSize,
                fontWeight = FontWeight.Medium,
                fontFamily = BlueprintFontFamily
            )
        }

    private fun buttonHeightFor(size: ButtonSize) = when (size) {
        ButtonSize.Regular -> ComponentTokens.ButtonRegularHeight
        ButtonSize.Compact -> ComponentTokens.ButtonCompactHeight
    }

    private fun buttonLabelSizeFor(size: ButtonSize) = when (size) {
        ButtonSize.Regular -> ComponentTokens.ButtonRegularLabelSize
        ButtonSize.Compact -> ComponentTokens.ButtonCompactLabelSize
    }

    private fun Color.tappableCardBorderColor(): Color =
        if (luminance() > 0.5f) {
            Color.Black.copy(alpha = 0.05f)
        } else {
            Color.White.copy(alpha = 0.05f)
        }
}
