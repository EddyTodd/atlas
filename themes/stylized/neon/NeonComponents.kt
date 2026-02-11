package com.ynmidk.atlas.themes.stylized.neon

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.ynmidk.atlas.core.ButtonSize
import com.ynmidk.atlas.core.ButtonVariant
import com.ynmidk.atlas.core.BaseAtlasComponents
import com.ynmidk.atlas.theme.LocalAtlasTypography
import com.ynmidk.atlas.theme.LocalColors
import com.ynmidk.atlas.theme.AtlasTextStyle
import com.ynmidk.atlas.core.ComponentTokens

object NeonThemeComponents : BaseAtlasComponents() {

    @Composable
    override fun Text(text: String, style: AtlasTextStyle) {
        val colors = LocalColors.current
        val typography = LocalAtlasTypography.current
        val glowColor = when (style) {
            AtlasTextStyle.DisplayTitle,
            AtlasTextStyle.Title,
            AtlasTextStyle.SectionTitle -> colors.accent

            AtlasTextStyle.CardTitle,
            AtlasTextStyle.BodyStrong,
            AtlasTextStyle.Label -> colors.primary

            AtlasTextStyle.Subtitle,
            AtlasTextStyle.Body,
            AtlasTextStyle.CardSubtitle,
            AtlasTextStyle.Muted,
            AtlasTextStyle.Caption,
            AtlasTextStyle.Overline -> colors.primary
        }
        val isMuted = style == AtlasTextStyle.Muted ||
            style == AtlasTextStyle.Caption ||
            style == AtlasTextStyle.CardSubtitle ||
            style == AtlasTextStyle.Overline

        Text(
            text = text,
            style = typography.textStyle(style).copy(
                shadow = Shadow(
                    color = glowColor.copy(alpha = if (isMuted) 0.55f else 0.9f),
                    offset = Offset.Zero,
                    blurRadius = if (isMuted) 6f else 12f
                )
            ),
            color = if (isMuted) colors.textMuted else colors.text
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
        val typography = LocalAtlasTypography.current
        val height = if (size == ButtonSize.Regular) {
            ComponentTokens.ButtonRegularHeight
        } else {
            ComponentTokens.ButtonCompactHeight
        }

        val borderColor = when (variant) {
            ButtonVariant.Primary -> colors.accent
            ButtonVariant.Secondary,
            ButtonVariant.Outline -> colors.primary
            ButtonVariant.Text,
            ButtonVariant.TextMuted -> Color.Transparent
        }

        val labelStyle = typography.buttonTextStyle(variant, size).copy(
            fontWeight = FontWeight.Bold,
            shadow = Shadow(
                color = borderColor.copy(alpha = 0.9f),
                offset = Offset.Zero,
                blurRadius = 8f
            )
        )

        when (variant) {
            ButtonVariant.Primary -> {
                Button(
                    onClick = onClick,
                    enabled = enabled,
                    modifier = modifier.height(height),
                    contentPadding = ComponentTokens.ButtonContentPadding,
                    shape = androidx.compose.foundation.shape.RoundedCornerShape(14.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colors.accent.copy(alpha = 0.22f),
                        contentColor = colors.text,
                        disabledContainerColor = colors.accent.copy(alpha = 0.12f),
                        disabledContentColor = colors.textMuted
                    ),
                    border = BorderStroke(1.dp, borderColor)
                ) {
                    Text(text = label, style = labelStyle)
                }
            }

            ButtonVariant.Secondary,
            ButtonVariant.Outline -> {
                OutlinedButton(
                    onClick = onClick,
                    enabled = enabled,
                    modifier = modifier.height(height),
                    contentPadding = ComponentTokens.ButtonContentPadding,
                    shape = androidx.compose.foundation.shape.RoundedCornerShape(14.dp),
                    border = BorderStroke(1.dp, borderColor),
                    colors = ButtonDefaults.outlinedButtonColors(
                        containerColor = colors.bg.copy(alpha = 0.18f),
                        contentColor = if (variant == ButtonVariant.Outline) colors.primary else colors.text,
                        disabledContentColor = colors.textMuted
                    )
                ) {
                    Text(text = label, style = labelStyle)
                }
            }

            ButtonVariant.Text,
            ButtonVariant.TextMuted -> {
                TextButton(
                    onClick = onClick,
                    enabled = enabled,
                    modifier = modifier.height(height),
                    contentPadding = ComponentTokens.ButtonContentPadding,
                    colors = ButtonDefaults.textButtonColors(
                        contentColor = if (variant == ButtonVariant.Text) colors.accent else colors.textMuted,
                        disabledContentColor = colors.textMuted.copy(alpha = 0.45f)
                    )
                ) {
                    Text(text = label, style = labelStyle)
                }
            }
        }
    }
}
