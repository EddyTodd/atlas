package com.ynmidk.atlas.core

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.ynmidk.atlas.theme.AtlasTextStyle
import com.ynmidk.atlas.theme.LocalAtlasComponents
import com.ynmidk.atlas.theme.LocalColors
import androidx.compose.material3.Slider as MaterialSlider

enum class ButtonVariant {
    Primary,
    Secondary,
    Outline,
    Text,
    TextMuted
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

    /* ──────────────── Dialogs ──────────────── */

    @Composable
    open fun AlertDialog(
        title: String,
        text: String,
        onDismiss: () -> Unit,
        confirmLabel: String = "OK",
        dismissLabel: String? = "Cancel",
        onConfirm: (() -> Unit)? = null
    ) {
        notImplemented("AlertDialog")
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
fun AtlasThemeOptionCard(
    name: String,
    preview: (@Composable BoxScope.() -> Unit)?,
    isActive: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val c = LocalAtlasComponents.current
    val content: @Composable () -> Unit = {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            AtlasThemePreviewSurface(preview = preview)
            c.Text(name, AtlasTextStyle.Caption)
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
            .heightIn(min = 54.dp)
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
            .height(46.dp)
            .border(
                width = 1.dp,
                color = colors.btnBorder,
                shape = RoundedCornerShape(10.dp)
            )
    )
}
