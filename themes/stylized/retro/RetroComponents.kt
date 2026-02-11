package com.ynmidk.atlas.themes.stylized.retro

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.height
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.ynmidk.atlas.core.ButtonSize
import com.ynmidk.atlas.core.ButtonVariant
import com.ynmidk.atlas.core.BaseAtlasComponents
import com.ynmidk.atlas.theme.LocalAtlasTypography
import com.ynmidk.atlas.theme.LocalColors
import com.ynmidk.atlas.theme.AtlasTextStyle
import com.ynmidk.atlas.core.ComponentTokens

object RetroThemeComponents : BaseAtlasComponents() {

    @Composable
    override fun Text(text: String, style: AtlasTextStyle) {
        val colors = LocalColors.current
        val typography = LocalAtlasTypography.current
        val isMuted = style == AtlasTextStyle.Muted ||
            style == AtlasTextStyle.Caption ||
            style == AtlasTextStyle.CardSubtitle ||
            style == AtlasTextStyle.Overline

        Text(
            text = text,
            style = typography.textStyle(style).copy(
                fontFamily = if (
                    style == AtlasTextStyle.DisplayTitle ||
                    style == AtlasTextStyle.Title ||
                    style == AtlasTextStyle.SectionTitle
                ) FontFamily.Monospace else typography.textStyle(style).fontFamily,
                shadow = Shadow(
                    color = colors.text.copy(alpha = 0.22f),
                    offset = Offset(1f, 1f),
                    blurRadius = 0f
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
            ButtonVariant.Primary -> colors.text
            ButtonVariant.Secondary -> colors.btnBorder
            ButtonVariant.Outline -> colors.accent
            ButtonVariant.Text,
            ButtonVariant.TextMuted -> Color.Transparent
        }

        val labelStyle = typography.buttonTextStyle(variant, size).copy(
            fontWeight = FontWeight.Bold,
            shadow = Shadow(
                color = colors.text.copy(alpha = 0.18f),
                offset = Offset(1f, 1f),
                blurRadius = 0f
            )
        )

        when (variant) {
            ButtonVariant.Text,
            ButtonVariant.TextMuted -> {
                TextButton(
                    onClick = onClick,
                    enabled = enabled,
                    modifier = modifier.height(height),
                    contentPadding = ComponentTokens.ButtonContentPadding,
                    colors = ButtonDefaults.textButtonColors(
                        contentColor = if (variant == ButtonVariant.Text) colors.text else colors.textMuted,
                        disabledContentColor = colors.textMuted.copy(alpha = 0.45f)
                    )
                ) {
                    Text(text = label, style = labelStyle)
                }
            }

            else -> {
                OutlinedButton(
                    onClick = onClick,
                    enabled = enabled,
                    modifier = modifier.height(height),
                    contentPadding = ComponentTokens.ButtonContentPadding,
                    shape = androidx.compose.foundation.shape.RoundedCornerShape(8.dp),
                    border = BorderStroke(1.dp, borderColor),
                    colors = ButtonDefaults.outlinedButtonColors(
                        containerColor = if (variant == ButtonVariant.Primary) colors.cardBg else colors.bg,
                        contentColor = if (variant == ButtonVariant.Outline) colors.accent else colors.text,
                        disabledContentColor = colors.textMuted.copy(alpha = 0.45f)
                    )
                ) {
                    Text(text = label, style = labelStyle)
                }
            }
        }
    }
}
