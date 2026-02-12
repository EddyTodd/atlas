package com.ynmidk.atlas.themes.stylized.neon

import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
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
import java.util.Locale

object NeonThemeComponents : BaseAtlasComponents() {

    private val neonFontFamily = FontFamily.SansSerif

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
                    NeonText(
                        text = title,
                        glowColor = NeonTokens.TitleGlowColor,
                        textColor = NeonTokens.White,
                        fontSize = NeonTokens.PageTitleSize,
                        fontWeight = FontWeight.Bold,
                        fontFamily = neonFontFamily
                    )
                }
            },
            navigationIcon = {
                if (onLeadingIconClick != null) {
                    NeonIconButton(
                        onClick = onLeadingIconClick,
                        modifier = Modifier,
                        glowColor = NeonTokens.Cyan
                    ) {
                        Icon(imageVector = atlasIcon(IconRole.Back), contentDescription = "Back")
                    }
                }
            },
            actions = {
                if (onTrailingIconClick != null) {
                    NeonIconButton(
                        onClick = onTrailingIconClick,
                        modifier = Modifier,
                        glowColor = NeonTokens.Cyan
                    ) {
                        Icon(
                            imageVector = atlasIcon(IconRole.Settings),
                            contentDescription = "Settings"
                        )
                    }
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
        val resolvedText = if (style == AtlasTextStyle.Subtitle) {
            text.uppercase(Locale.getDefault())
        } else {
            text
        }
        val glow = when (style) {
            AtlasTextStyle.DisplayTitle,
            AtlasTextStyle.Title,
            AtlasTextStyle.SectionTitle -> NeonTokens.TitleGlowColor

            AtlasTextStyle.Subtitle -> NeonTokens.Magenta
            AtlasTextStyle.CardTitle,
            AtlasTextStyle.Body,
            AtlasTextStyle.BodyStrong,
            AtlasTextStyle.Label -> NeonTokens.Cyan

            AtlasTextStyle.CardSubtitle,
            AtlasTextStyle.Muted,
            AtlasTextStyle.Caption,
            AtlasTextStyle.Overline -> NeonTokens.Cyan.copy(alpha = 0.7f)
        }
        val size = when (style) {
            AtlasTextStyle.DisplayTitle -> ComponentTokens.DisplayTitleSize
            AtlasTextStyle.Title -> ComponentTokens.TitleSize
            AtlasTextStyle.Subtitle -> ComponentTokens.SubtitleSize
            AtlasTextStyle.SectionTitle -> ComponentTokens.SectionTitleSize
            AtlasTextStyle.CardTitle -> ComponentTokens.CardTitleSize
            AtlasTextStyle.CardSubtitle -> ComponentTokens.CardSubtitleSize
            AtlasTextStyle.Body,
            AtlasTextStyle.BodyStrong -> ComponentTokens.BodySize

            AtlasTextStyle.Label -> ComponentTokens.LabelSize
            AtlasTextStyle.Muted -> ComponentTokens.MutedSize
            AtlasTextStyle.Caption -> ComponentTokens.CaptionSize
            AtlasTextStyle.Overline -> ComponentTokens.OverlineSize
        }
        NeonText(
            text = resolvedText,
            glowColor = glow,
            textColor = NeonTokens.White,
            fontSize = size,
            fontWeight = if (
                style == AtlasTextStyle.DisplayTitle ||
                style == AtlasTextStyle.Title ||
                style == AtlasTextStyle.Subtitle ||
                style == AtlasTextStyle.SectionTitle ||
                style == AtlasTextStyle.CardTitle ||
                style == AtlasTextStyle.BodyStrong ||
                style == AtlasTextStyle.Label
            ) {
                FontWeight.Bold
            } else {
                FontWeight.Medium
            },
            fontFamily = neonFontFamily
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
                NeonButton(onClick, enabled, NeonTokens.Magenta, size, modifier) {
                    NeonText(
                        text = label,
                        glowColor = if (enabled) NeonTokens.Magenta else Color.Transparent,
                        textColor = if (enabled) NeonTokens.White else NeonTokens.ButtonDisabledColor,
                        fontSize = buttonLabelSizeFor(size),
                        fontWeight = FontWeight.Bold,
                        fontFamily = neonFontFamily,
                        modifier = Modifier.padding(horizontal = primaryButtonTextPadding(size))
                    )
                }
            }

            ButtonVariant.Secondary -> {
                NeonButton(onClick, enabled, NeonTokens.Cyan, size, modifier) {
                    NeonText(
                        text = label,
                        glowColor = if (enabled) NeonTokens.Cyan else Color.Transparent,
                        textColor = if (enabled) NeonTokens.White else NeonTokens.ButtonDisabledColor,
                        fontSize = buttonLabelSizeFor(size),
                        fontWeight = FontWeight.Bold,
                        fontFamily = neonFontFamily
                    )
                }
            }

            ButtonVariant.Outline -> {
                NeonButton(onClick, enabled, NeonTokens.White, size, modifier) {
                    NeonText(
                        text = label,
                        glowColor = if (enabled) NeonTokens.Cyan else Color.Transparent,
                        textColor = if (enabled) NeonTokens.White else NeonTokens.ButtonDisabledColor,
                        fontSize = buttonLabelSizeFor(size),
                        fontWeight = FontWeight.Bold,
                        fontFamily = neonFontFamily
                    )
                }
            }

            ButtonVariant.Text -> {
                TextButton(
                    onClick = onClick,
                    enabled = enabled,
                    modifier = modifier.height(buttonHeightFor(size)),
                    contentPadding = ComponentTokens.ButtonContentPadding,
                    colors = ButtonDefaults.textButtonColors(
                        containerColor = Color.Transparent,
                        contentColor = if (enabled) NeonTokens.White else NeonTokens.ButtonDisabledColor,
                        disabledContentColor = NeonTokens.ButtonDisabledColor
                    )
                ) {
                    NeonText(
                        text = label,
                        glowColor = if (enabled) NeonTokens.Magenta else Color.Transparent,
                        textColor = if (enabled) NeonTokens.White else NeonTokens.ButtonDisabledColor,
                        fontSize = buttonLabelSizeFor(size),
                        fontWeight = FontWeight.Bold,
                        fontFamily = neonFontFamily
                    )
                }
            }

            ButtonVariant.TextMuted -> {
                TextButton(
                    onClick = onClick,
                    enabled = enabled,
                    modifier = modifier.height(buttonHeightFor(size)),
                    contentPadding = ComponentTokens.ButtonContentPadding,
                    colors = ButtonDefaults.textButtonColors(
                        containerColor = Color.Transparent,
                        contentColor = if (enabled) NeonTokens.White else NeonTokens.ButtonDisabledColor,
                        disabledContentColor = NeonTokens.ButtonDisabledColor
                    )
                ) {
                    NeonText(
                        text = label,
                        glowColor = if (enabled) NeonTokens.Cyan else Color.Transparent,
                        textColor = if (enabled) NeonTokens.White else NeonTokens.ButtonDisabledColor,
                        fontSize = buttonLabelSizeFor(size),
                        fontWeight = FontWeight.Bold,
                        fontFamily = neonFontFamily
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
        val glowColor = when (variant) {
            IconButtonVariant.Primary -> NeonTokens.Magenta
            IconButtonVariant.Secondary,
            IconButtonVariant.Outlined -> NeonTokens.Cyan

            IconButtonVariant.Colored -> tintColor ?: NeonTokens.Cyan
        }
        NeonIconButton(
            onClick = onClick,
            modifier = Modifier.size(
                if (size == ButtonSize.Regular) {
                    ComponentTokens.ButtonRegularHeight
                } else {
                    ComponentTokens.ButtonCompactHeight
                }
            ),
            glowColor = glowColor,
            enabled = enabled
        ) {
            Icon(imageVector = atlasIcon(role), contentDescription = role.javaClass.simpleName)
        }
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
            val borderColor = colors.cardBg.tappableCardBorderColor()
            val interactionSource = remember { MutableInteractionSource() }
            val cardShape = RoundedCornerShape(NeonTokens.CardCornerRadius)
            Card(
                onClick = onClick ?: {},
                enabled = enabled,
                interactionSource = interactionSource,
                modifier = modifier
                    .neonSurface(
                        glowColor = borderColor,
                        cornerRadius = NeonTokens.CardCornerRadius,
                        brush = NeonTokens.SurfaceBrush,
                        intensity = NeonTokens.CardInactiveIntensity,
                        borderWidth = NeonTokens.ButtonBorderWidth,
                        borderGlowColor = borderColor,
                        glowBlur = NeonTokens.ButtonGlowBlur,
                        glowAlpha = NeonTokens.ButtonGlowAlpha
                    )
                    .border(BaseTokens.BorderWidth, borderColor, shape = cardShape),
                colors = CardDefaults.cardColors(containerColor = Color.Transparent),
                elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
            ) {
                Box(modifier = Modifier.padding(contentPadding)) { content() }
            }
            return
        }

        val borderGlow = if (style == CardStyle.Active) NeonTokens.Magenta else NeonTokens.Cyan
        val intensity =
            if (style == CardStyle.Active) NeonTokens.CardActiveIntensity else NeonTokens.CardInactiveIntensity

        Card(
            modifier = modifier.neonSurface(
                glowColor = NeonTokens.White,
                cornerRadius = NeonTokens.CardCornerRadius,
                brush = NeonTokens.SurfaceBrush,
                intensity = intensity,
                borderWidth = NeonTokens.ButtonBorderWidth,
                borderGlowColor = borderGlow,
                glowBlur = NeonTokens.ButtonGlowBlur,
                glowAlpha = NeonTokens.ButtonGlowAlpha
            ),
            colors = CardDefaults.cardColors(containerColor = Color.Transparent),
            elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
        ) {
            Box(modifier = Modifier.padding(contentPadding)) { content() }
        }
    }

    @Composable
    override fun ScreenBackground(
        modifier: Modifier,
        content: @Composable () -> Unit
    ) {
        Box(modifier = modifier.neonScreenOverlay()) {
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
                disabledCheckedTrackColor = colors.primary.copy(alpha = NeonTokens.DisabledTrackAlpha),
                disabledCheckedThumbColor = colors.bg.copy(alpha = NeonTokens.DisabledThumbAlpha)
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
                disabledThumbColor = colors.primary.copy(alpha = NeonTokens.DisabledTrackAlpha),
                disabledActiveTrackColor = colors.primary.copy(alpha = NeonTokens.DisabledTrackAlpha),
                disabledActiveTickColor = colors.bg.copy(alpha = NeonTokens.DisabledThumbAlpha),
                disabledInactiveTrackColor = colors.dotInactive.copy(alpha = NeonTokens.DisabledInactiveTrackAlpha),
                disabledInactiveTickColor = colors.dotInactive.copy(alpha = NeonTokens.DisabledInactiveTrackAlpha)
            )
        )
    }

    @Composable
    override fun Divider() {
        HorizontalDivider(
            color = NeonTokens.Cyan.copy(alpha = NeonTokens.DividerAlpha),
            thickness = NeonTokens.DividerThickness
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
        val confirmAction = onConfirm ?: onDismiss
        AlertDialog(
            onDismissRequest = onDismiss,
            confirmButton = {
                TextButton(onClick = confirmAction) {
                    NeonText(
                        text = confirmLabel,
                        glowColor = NeonTokens.Magenta,
                        textColor = NeonTokens.White,
                        fontSize = NeonTokens.DialogButtonSize,
                        fontWeight = FontWeight.Bold,
                        fontFamily = neonFontFamily
                    )
                }
            },
            dismissButton = if (dismissLabel != null) {
                {
                    TextButton(onClick = onDismiss) {
                        NeonText(
                            text = dismissLabel,
                            glowColor = NeonTokens.Cyan,
                            textColor = NeonTokens.White,
                            fontSize = NeonTokens.DialogButtonSize,
                            fontWeight = FontWeight.Bold,
                            fontFamily = neonFontFamily
                        )
                    }
                }
            } else {
                null
            },
            title = {
                NeonText(
                    text = title,
                    glowColor = NeonTokens.Cyan,
                    textColor = NeonTokens.White,
                    fontSize = NeonTokens.DialogTitleSize,
                    fontWeight = FontWeight.Bold,
                    fontFamily = neonFontFamily
                )
            },
            text = {
                NeonText(
                    text = text,
                    glowColor = NeonTokens.Cyan,
                    textColor = NeonTokens.White,
                    fontSize = NeonTokens.DialogTextSize,
                    fontWeight = FontWeight.Medium,
                    fontFamily = neonFontFamily
                )
            },
            containerColor = NeonTokens.SurfaceStart,
            modifier = Modifier.neonSurface(
                glowColor = NeonTokens.White,
                cornerRadius = NeonTokens.DialogCornerRadius,
                brush = NeonTokens.SurfaceBrush,
                intensity = NeonTokens.DialogIntensity,
                borderWidth = NeonTokens.ButtonBorderWidth,
                borderGlowColor = NeonTokens.Cyan,
                glowBlur = NeonTokens.ButtonGlowBlur,
                glowAlpha = NeonTokens.ButtonGlowAlpha
            )
        )
    }

    @Composable
    private fun NeonButton(
        onClick: () -> Unit,
        enabled: Boolean,
        glowColor: Color,
        size: ButtonSize,
        modifier: Modifier,
        content: @Composable RowScope.() -> Unit
    ) {
        val coreBrush = if (enabled) NeonTokens.SurfaceBrush else SolidColor(Color.Transparent)
        val borderGlow = if (enabled) glowColor else null
        val borderColor = if (enabled) NeonTokens.White else NeonTokens.ButtonDisabledColor
        val glowBlur = if (enabled) NeonTokens.ButtonGlowBlur else 0.dp
        val glowAlpha = if (enabled) NeonTokens.ButtonGlowAlpha else 0f
        val contentColor = if (enabled) NeonTokens.White else NeonTokens.ButtonDisabledColor
        Button(
            onClick = onClick,
            enabled = enabled,
            modifier = modifier
                .height(buttonHeightFor(size))
                .neonSurface(
                    glowColor = borderColor,
                    cornerRadius = NeonTokens.ButtonCornerRadius,
                    brush = coreBrush,
                    intensity = if (enabled) {
                        NeonTokens.ButtonEnabledIntensity
                    } else {
                        NeonTokens.ButtonDisabledIntensity
                    },
                    borderWidth = NeonTokens.ButtonBorderWidth,
                    borderGlowColor = borderGlow,
                    glowBlur = glowBlur,
                    glowAlpha = glowAlpha
                ),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Transparent,
                contentColor = contentColor,
                disabledContentColor = NeonTokens.ButtonDisabledColor
            ),
            contentPadding = ComponentTokens.ButtonContentPadding,
            shape = RoundedCornerShape(NeonTokens.ButtonCornerRadius)
        ) {
            content()
        }
    }

    private fun buttonHeightFor(size: ButtonSize) = when (size) {
        ButtonSize.Regular -> ComponentTokens.ButtonRegularHeight
        ButtonSize.Compact -> ComponentTokens.ButtonCompactHeight
    }

    private fun buttonLabelSizeFor(size: ButtonSize) = when (size) {
        ButtonSize.Regular -> ComponentTokens.ButtonRegularLabelSize
        ButtonSize.Compact -> ComponentTokens.ButtonCompactLabelSize
    }

    @Composable
    private fun NeonIconButton(
        onClick: () -> Unit,
        modifier: Modifier,
        glowColor: Color,
        enabled: Boolean = true,
        content: @Composable () -> Unit
    ) {
        val coreBrush = if (enabled) NeonTokens.SurfaceBrush else SolidColor(Color.Transparent)
        val borderGlow = if (enabled) glowColor else null
        val borderColor = if (enabled) NeonTokens.White else NeonTokens.ButtonDisabledColor
        val glowBlur = if (enabled) NeonTokens.ButtonGlowBlur else 0.dp
        val glowAlpha = if (enabled) NeonTokens.ButtonGlowAlpha else 0f
        val contentGlow = if (enabled) glowColor else Color.Transparent
        val contentColor = if (enabled) NeonTokens.White else NeonTokens.ButtonDisabledColor
        FilledIconButton(
            onClick = onClick,
            enabled = enabled,
            shape = CircleShape,
            colors = IconButtonDefaults.filledIconButtonColors(
                containerColor = Color.Transparent,
                contentColor = contentColor,
                disabledContentColor = NeonTokens.ButtonDisabledColor
            ),
            modifier = modifier
                .neonSurface(
                    glowColor = borderColor,
                    cornerRadius = NeonTokens.DialogCornerRadius,
                    brush = coreBrush,
                    intensity = if (enabled) {
                        NeonTokens.ButtonEnabledIntensity
                    } else {
                        NeonTokens.ButtonDisabledIntensity
                    },
                    borderWidth = NeonTokens.ButtonBorderWidth,
                    borderGlowColor = borderGlow,
                    glowBlur = glowBlur,
                    glowAlpha = glowAlpha
                )
        ) {
            NeonGlowContent(glowColor = contentGlow, contentColor = contentColor) { content() }
        }
    }

    private fun Color.tappableCardBorderColor(): Color =
        if (luminance() > 0.5f) {
            Color.Black.copy(alpha = 0.05f)
        } else {
            Color.White.copy(alpha = 0.05f)
        }
}
