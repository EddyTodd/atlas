package com.ynmidk.atlas.core

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.background
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.ynmidk.atlas.theme.AtlasTextStyle
import com.ynmidk.atlas.theme.LocalAtlasTypography
import com.ynmidk.atlas.theme.LocalAtlasComponents
import com.ynmidk.atlas.theme.LocalColors
import androidx.compose.material3.Slider as MaterialSlider

enum class ButtonVariant {
    Primary,
    Secondary,
    Outline,
    Text,
    TextCaption
}

enum class ButtonSize {
    Regular,
    Compact
}

enum class IconButtonVariant {
    Primary,
    Secondary,
    Outlined,
    Colored
}

enum class CardStyle {
    Regular,
    Tappable,
    Active
}

open class AtlasComponents {

    /* ──────────────── Top / Text ──────────────── */

    @Composable
    open fun TopBar(
        title: String? = null,
        onLeadingIconClick: (() -> Unit)? = null,
        onTrailingIconClick: (() -> Unit)? = null
    ) {
        notImplemented("TopBar")
    }

    @Composable
    open fun Text(
        text: String,
        style: AtlasTextStyle
    ) {
        notImplemented("Text")
    }

    /* ──────────────── Navigation ──────────────── */

    @Composable
    open fun Tabs(
        labels: List<String>,
        selectedIndex: Int,
        onSelect: (Int) -> Unit
    ) {
        notImplemented("Tabs")
    }

    /* ──────────────── Buttons ──────────────── */

    @Composable
    open fun Button(
        variant: ButtonVariant,
        size: ButtonSize = ButtonSize.Regular,
        enabled: Boolean,
        onClick: () -> Unit,
        label: String,
        modifier: Modifier = Modifier,
        heightOverride: Dp? = null
    ) {
        notImplemented("Button")
    }

    @Composable
    open fun IconButton(
        variant: IconButtonVariant,
        size: ButtonSize = ButtonSize.Regular,
        role: IconRole,
        enabled: Boolean,
        tintColor: Color? = null,
        onClick: () -> Unit,
        modifier: Modifier = Modifier,
        sizeOverride: Dp? = null
    ) {
        notImplemented("IconButton")
    }

    @Composable
    open fun InfoButton(
        onClick: () -> Unit,
        modifier: Modifier = Modifier,
        label: String = "i"
    ) {
        val colors = LocalColors.current
        val typography = LocalAtlasTypography.current
        Box(
            modifier = modifier
                .size(18.dp)
                .background(
                    color = colors.btnBorder.copy(alpha = 0.25f),
                    shape = CircleShape
                )
                .clickable(onClick = onClick),
            contentAlignment = Alignment.Center
        ) {
            androidx.compose.material3.Text(
                text = label,
                color = Color.White,
                style = typography.textStyle(AtlasTextStyle.Overline)
            )
        }
    }

    /* ──────────────── Containers / Controls ──────────────── */

    @Composable
    open fun Card(
        modifier: Modifier = Modifier,
        style: CardStyle = CardStyle.Regular,
        enabled: Boolean = true,
        onClick: (() -> Unit)? = null,
        contentPadding: PaddingValues = PaddingValues(BaseTokens.CardPadding),
        content: @Composable () -> Unit
    ) {
        notImplemented("Card")
    }

    @Composable
    open fun ScreenBackground(
        modifier: Modifier = Modifier,
        content: @Composable () -> Unit
    ) {
        notImplemented("ScreenBackground")
    }

    @Composable
    open fun AppLogoBadge(
        modifier: Modifier = Modifier,
        badgeSize: Dp = 72.dp
    ) {
        notImplemented("AppLogoBadge")
    }

    @Composable
    open fun Toggle(
        checked: Boolean,
        enabled: Boolean,
        onCheckedChange: (Boolean) -> Unit
    ) {
        notImplemented("Toggle")
    }

    @Composable
    open fun Slider(
        value: Float,
        enabled: Boolean,
        onValueChange: (Float) -> Unit,
        valueRange: ClosedFloatingPointRange<Float> = 0f..1f,
        steps: Int = 0,
        onValueChangeFinished: (() -> Unit)? = null
    ) {
        MaterialSlider(
            value = value,
            onValueChange = onValueChange,
            enabled = enabled,
            valueRange = valueRange,
            steps = steps,
            onValueChangeFinished = onValueChangeFinished
        )
    }


    @Composable
    open fun Divider() {
        notImplemented("Divider")
    }

    @Composable
    open fun Tooltip(
        text: String,
        expanded: Boolean,
        onDismiss: () -> Unit,
        modifier: Modifier = Modifier
    ) {
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = onDismiss,
            modifier = modifier,
            offset = DpOffset(x = 0.dp, y = (-8).dp),
            containerColor = Color.Transparent,
            tonalElevation = 0.dp,
            shadowElevation = 0.dp
        ) {
            Box(
                modifier = Modifier.padding(
                    top = 4.dp,
                    start = 6.dp,
                    end = 6.dp,
                    bottom = 6.dp
                )
            ) {
                Card(
                    modifier = Modifier.widthIn(max = 280.dp),
                    contentPadding = PaddingValues(horizontal = 12.dp, vertical = 8.dp)
                ) {
                    Text(text, AtlasTextStyle.Caption)
                }
            }
        }
    }

    /* ──────────────── Dialogs ──────────────── */

    @Composable
    open fun ConfirmDialog(
        title: String,
        text: String,
        onDismiss: () -> Unit,
        confirmLabel: String = "OK",
        dismissLabel: String? = "Cancel",
        onConfirm: (() -> Unit)? = null
    ) {
        val confirmAction = onConfirm ?: onDismiss
        Dialog(onDismissRequest = onDismiss) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
            ) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .widthIn(max = 420.dp),
                    contentPadding = PaddingValues(20.dp)
                ) {
                    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                        Text(title, AtlasTextStyle.SectionTitle)
                        Text(text, AtlasTextStyle.Body)
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            if (dismissLabel != null) {
                                Button(
                                    modifier = Modifier.weight(1f),
                                    variant = ButtonVariant.Outline,
                                    enabled = true,
                                    onClick = onDismiss,
                                    label = dismissLabel
                                )
                            }
                            Button(
                                modifier = Modifier.weight(1f),
                                variant = ButtonVariant.Primary,
                                enabled = true,
                                onClick = confirmAction,
                                label = confirmLabel
                            )
                        }
                    }
                }
            }
        }
    }

    @Composable
    open fun AlertDialog(
        title: String,
        text: String,
        onDismiss: () -> Unit,
        confirmLabel: String = "OK",
        dismissLabel: String? = "Cancel",
        onConfirm: (() -> Unit)? = null
    ) {
        ConfirmDialog(
            title = title,
            text = text,
            onDismiss = onDismiss,
            confirmLabel = confirmLabel,
            dismissLabel = dismissLabel,
            onConfirm = onConfirm
        )
    }

    @Composable
    open fun BottomDrawer(
        onDismiss: () -> Unit,
        content: @Composable () -> Unit
    ) {
        notImplemented("BottomDrawer")
    }

    private fun notImplemented(componentName: String): Nothing {
        error("$componentName is not implemented by this AtlasComponents instance")
    }
}

@Composable
fun AtlasTooltip(
    text: String,
    expanded: Boolean,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {
    LocalAtlasComponents.current.Tooltip(
        text = text,
        expanded = expanded,
        onDismiss = onDismiss,
        modifier = modifier
    )
}

@Composable
fun AtlasInfoButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    label: String = "i"
) {
    LocalAtlasComponents.current.InfoButton(
        onClick = onClick,
        modifier = modifier,
        label = label
    )
}

@Composable
fun <T> AtlasDividedCard(
    items: List<T>,
    modifier: Modifier = Modifier,
    style: CardStyle = CardStyle.Regular,
    enabled: Boolean = true,
    onClick: (() -> Unit)? = null,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    row: @Composable (item: T) -> Unit
) {
    val c = LocalAtlasComponents.current
    c.Card(
        modifier = modifier,
        style = style,
        enabled = enabled,
        onClick = onClick,
        contentPadding = contentPadding
    ) {
        Column {
            items.forEachIndexed { index, item ->
                row(item)
                if (index < items.lastIndex) {
                    c.Divider()
                }
            }
        }
    }
}

@Composable
fun AtlasThemeOptionCard(
    name: String,
    iconName: String? = null,
    preview: (@Composable BoxScope.() -> Unit)?,
    isActive: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val c = LocalAtlasComponents.current
    val colors = LocalColors.current
    val context = LocalContext.current
    val iconRes = remember(iconName, context.packageName) {
        iconName?.let { nameKey ->
            context.resources.getIdentifier(nameKey, "drawable", context.packageName)
        } ?: 0
    }
    val content: @Composable () -> Unit = {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            AtlasThemePreviewSurface(preview = preview)
            if (iconRes != 0) {
                Icon(
                    painter = painterResource(iconRes),
                    contentDescription = name,
                    tint = if (isActive) colors.primary else colors.textCaption,
                    modifier = Modifier.size(24.dp)
                )
            } else {
                c.Text(name, AtlasTextStyle.Caption)
            }
        }
    }

    c.Card(
        modifier = modifier,
        style = if (isActive) CardStyle.Active else CardStyle.Tappable,
        onClick = if (isActive) null else onClick,
        content = content
    )
}

@Composable
fun AtlasThemePreviewSurface(
    preview: (@Composable BoxScope.() -> Unit)?
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1f)
    ) {
        preview?.invoke(this) ?: AtlasThemePreviewPlaceholder()
    }
}

@Composable
private fun AtlasThemePreviewPlaceholder() {
    val colors = LocalColors.current
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1f)
            .border(
                width = 1.dp,
                color = colors.btnBorder,
                shape = RoundedCornerShape(10.dp)
            )
    )
}
