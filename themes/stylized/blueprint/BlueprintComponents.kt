package com.ynmidk.atlas.themes.stylized.blueprint

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.ynmidk.atlas.core.ButtonSize
import com.ynmidk.atlas.core.ButtonVariant
import com.ynmidk.atlas.core.BaseAtlasComponents
import com.ynmidk.atlas.theme.LocalAtlasTypography
import com.ynmidk.atlas.theme.LocalColors
import com.ynmidk.atlas.theme.AtlasTextStyle
import com.ynmidk.atlas.core.ComponentTokens

object BlueprintThemeComponents : BaseAtlasComponents() {

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
                textDecoration = if (
                    style == AtlasTextStyle.DisplayTitle ||
                    style == AtlasTextStyle.Title ||
                    style == AtlasTextStyle.SectionTitle ||
                    style == AtlasTextStyle.CardTitle ||
                    style == AtlasTextStyle.Label
                ) TextDecoration.Underline else TextDecoration.None
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

        val labelStyle = typography.buttonTextStyle(variant, size).copy(
            fontWeight = FontWeight.Bold,
            textDecoration = TextDecoration.Underline
        )

        when (variant) {
            ButtonVariant.Primary -> {
                Button(
                    onClick = onClick,
                    enabled = enabled,
                    modifier = modifier.height(height),
                    contentPadding = ComponentTokens.ButtonContentPadding,
                    shape = androidx.compose.foundation.shape.RoundedCornerShape(10.dp),
                    border = BorderStroke(1.dp, colors.primary),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colors.secondaryCardBg,
                        contentColor = colors.primary,
                        disabledContainerColor = colors.secondaryCardBg,
                        disabledContentColor = colors.textMuted
                    )
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
                    shape = androidx.compose.foundation.shape.RoundedCornerShape(10.dp),
                    border = BorderStroke(
                        1.dp,
                        if (variant == ButtonVariant.Outline) colors.accent else colors.primary
                    ),
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = if (variant == ButtonVariant.Outline) colors.accent else colors.primary,
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
                        contentColor = if (variant == ButtonVariant.Text) colors.primary else colors.textMuted,
                        disabledContentColor = colors.textMuted.copy(alpha = 0.45f)
                    )
                ) {
                    Text(text = label, style = labelStyle)
                }
            }
        }
    }
}
