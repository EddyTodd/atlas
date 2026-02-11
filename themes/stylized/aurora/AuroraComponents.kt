package com.ynmidk.atlas.themes.stylized.aurora

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.height
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.Button as MaterialButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.ynmidk.atlas.core.ButtonSize
import com.ynmidk.atlas.core.ButtonVariant
import com.ynmidk.atlas.core.BaseAtlasComponents
import com.ynmidk.atlas.theme.LocalColors
import com.ynmidk.atlas.core.ComponentTokens

object AuroraThemeComponents : BaseAtlasComponents() {
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
        val height = if (size == ButtonSize.Regular) {
            ComponentTokens.ButtonRegularHeight
        } else {
            ComponentTokens.ButtonCompactHeight
        }
        val labelSize = if (size == ButtonSize.Regular) {
            ComponentTokens.ButtonRegularLabelSize
        } else {
            ComponentTokens.ButtonCompactLabelSize
        }
        val textLabel: @Composable () -> Unit = {
            Text(
                text = label,
                fontSize = labelSize,
                fontWeight = FontWeight.SemiBold
            )
        }

        when (variant) {
            ButtonVariant.Primary -> {
                MaterialButton(
                    onClick = onClick,
                    enabled = enabled,
                    modifier = modifier.height(height),
                    contentPadding = ComponentTokens.ButtonContentPadding,
                    shape = androidx.compose.foundation.shape.RoundedCornerShape(14.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colors.primary,
                        contentColor = colors.badgeText,
                        disabledContainerColor = colors.primary.copy(alpha = 0.35f),
                        disabledContentColor = colors.badgeText.copy(alpha = 0.6f)
                    )
                ) { textLabel() }
            }

            ButtonVariant.Secondary -> {
                OutlinedButton(
                    onClick = onClick,
                    enabled = enabled,
                    modifier = modifier.height(height),
                    contentPadding = ComponentTokens.ButtonContentPadding,
                    shape = androidx.compose.foundation.shape.RoundedCornerShape(14.dp),
                    border = BorderStroke(
                        width = 1.dp,
                        color = colors.primary.copy(alpha = if (enabled) 1f else 0.35f)
                    ),
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = colors.primary,
                        disabledContentColor = colors.primary.copy(alpha = 0.45f)
                    )
                ) { textLabel() }
            }

            ButtonVariant.Outline -> {
                OutlinedButton(
                    onClick = onClick,
                    enabled = enabled,
                    modifier = modifier.height(height),
                    contentPadding = ComponentTokens.ButtonContentPadding,
                    shape = androidx.compose.foundation.shape.RoundedCornerShape(14.dp),
                    border = BorderStroke(
                        width = 1.dp,
                        color = colors.accent.copy(alpha = if (enabled) 1f else 0.35f)
                    ),
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = colors.accent,
                        disabledContentColor = colors.accent.copy(alpha = 0.45f)
                    )
                ) { textLabel() }
            }

            ButtonVariant.Text -> {
                TextButton(
                    onClick = onClick,
                    enabled = enabled,
                    modifier = modifier.height(height),
                    contentPadding = ComponentTokens.ButtonContentPadding,
                    colors = ButtonDefaults.textButtonColors(
                        contentColor = colors.primary,
                        disabledContentColor = colors.primary.copy(alpha = 0.45f)
                    )
                ) { textLabel() }
            }

            ButtonVariant.TextMuted -> {
                TextButton(
                    onClick = onClick,
                    enabled = enabled,
                    modifier = modifier.height(height),
                    contentPadding = ComponentTokens.ButtonContentPadding,
                    colors = ButtonDefaults.textButtonColors(
                        contentColor = colors.textMuted,
                        disabledContentColor = colors.textMuted.copy(alpha = 0.45f)
                    )
                ) { textLabel() }
            }
        }
    }
}
