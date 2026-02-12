package com.ynmidk.atlas.core

import android.graphics.BlurMaskFilter
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.SecondaryTabRow
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.CompositingStrategy
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.ynmidk.atlas.theme.AtlasTextStyle
import com.ynmidk.atlas.theme.LocalAtlasTypography
import com.ynmidk.atlas.theme.LocalColors
import java.util.Locale
import android.graphics.Paint as AndroidPaint

open class BaseAtlasComponents : AtlasComponents() {

    private val cardShape = RoundedCornerShape(BaseTokens.CardCornerRadius)

    private companion object {
        const val LauncherIconResName = "ic_launcher_foreground"
    }

    @Composable
    @OptIn(ExperimentalMaterial3Api::class)
    override fun TopBar(
        title: String?,
        onLeadingIconClick: (() -> Unit)?,
        onTrailingIconClick: (() -> Unit)?
    ) {
        val colors = LocalColors.current
        val typography = LocalAtlasTypography.current

        TopAppBar(
            expandedHeight = BaseTokens.TopBarHeight,
            modifier = Modifier.padding(horizontal = 16.dp),
            navigationIcon = {
                if (onLeadingIconClick != null) {
                    IconButton(
                        role = IconRole.Back,
                        onClick = onLeadingIconClick,
                        enabled = true,
                        variant = IconButtonVariant.Secondary
                    )
                }
            },
            title = {
                if (title != null) {
                    Text(
                        text = title,
                        modifier = Modifier.padding(start = if (onLeadingIconClick != null) 12.dp else 0.dp),
                        style = typography.textStyle(AtlasTextStyle.Title)
                    )
                }
            },
            actions = {
                if (onTrailingIconClick != null) {
                    IconButton(
                        role = IconRole.Settings,
                        onClick = onTrailingIconClick,
                        enabled = true,
                        variant = IconButtonVariant.Secondary
                    )
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color.Transparent,
                titleContentColor = colors.text
            )
        )
    }

    @Composable
    override fun Tabs(
        labels: List<String>,
        selectedIndex: Int,
        onSelect: (Int) -> Unit
    ) {
        val colors = LocalColors.current
        val typography = LocalAtlasTypography.current
        if (labels.isEmpty()) return

        val clampedIndex = selectedIndex.coerceIn(0, labels.lastIndex)

        SecondaryTabRow(
            selectedTabIndex = clampedIndex,
            containerColor = colors.cardBg,
            contentColor = colors.text,
            indicator = {
                TabRowDefaults.SecondaryIndicator(
                    color = colors.primary
                )
            }
        ) {
            labels.forEachIndexed { index, label ->
                Tab(
                    selected = index == clampedIndex,
                    onClick = { onSelect(index) },
                    selectedContentColor = colors.text,
                    unselectedContentColor = colors.textMuted,
                    text = {
                        Text(
                            text = label,
                            style = typography.tabTextStyle()
                        )
                    }
                )
            }
        }
    }

    @Composable
    override fun Text(text: String, style: AtlasTextStyle) {
        val colors = LocalColors.current
        val typography = LocalAtlasTypography.current
        val resolvedText = if (style == AtlasTextStyle.Subtitle) {
            text.uppercase(Locale.getDefault())
        } else {
            text
        }
        val color = when (style) {
            AtlasTextStyle.DisplayTitle -> colors.text
            AtlasTextStyle.Title -> colors.text
            AtlasTextStyle.Subtitle -> colors.textMuted
            AtlasTextStyle.SectionTitle -> colors.text
            AtlasTextStyle.CardTitle -> colors.text
            AtlasTextStyle.CardSubtitle -> colors.textMuted
            AtlasTextStyle.Body -> colors.text
            AtlasTextStyle.BodyStrong -> colors.text
            AtlasTextStyle.Label -> colors.text
            AtlasTextStyle.Muted -> colors.textMuted
            AtlasTextStyle.Caption -> colors.textMuted
            AtlasTextStyle.Overline -> colors.textMuted
        }
        Text(
            text = resolvedText,
            style = typography.textStyle(style),
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
        val typography = LocalAtlasTypography.current
        val shape = buttonShapeFor(size)
        val labelTextStyle = typography.buttonTextStyle(variant, size)

        when (variant) {
            ButtonVariant.Primary -> {
                val shadowModifier = if (enabled) {
                    Modifier.primaryButtonShadow(
                        shadowColor = colors.primary.copy(alpha = BaseTokens.PrimaryShadowAlpha),
                        cornerRadius = if (size == ButtonSize.Regular) {
                            BaseTokens.ButtonCornerRadiusRegular
                        } else {
                            BaseTokens.ButtonCornerRadiusCompact
                        }
                    )
                } else {
                    Modifier
                }
                Button(
                    onClick = onClick,
                    enabled = enabled,
                    shape = shape,
                    contentPadding = buttonContentPaddingFor(variant, size),
                    modifier = modifier
                        .height(buttonHeightFor(size))
                        .then(shadowModifier),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colors.primary,
                        contentColor = colors.badgeText,
                        disabledContainerColor = colors.primary.copy(alpha = BaseTokens.DisabledPrimaryContainerAlpha),
                        disabledContentColor = colors.badgeText.copy(alpha = BaseTokens.DisabledPrimaryContentAlpha)
                    )
                ) {
                    Text(
                        label,
                        style = labelTextStyle
                    )
                }
            }

            ButtonVariant.Secondary -> {
                OutlinedButton(
                    onClick = onClick,
                    enabled = enabled,
                    shape = shape,
                    contentPadding = buttonContentPaddingFor(variant, size),
                    modifier = modifier.height(buttonHeightFor(size)),
                    border = BorderStroke(BaseTokens.BorderWidth, colors.btnBorder),
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = colors.btnText,
                        disabledContentColor = colors.btnText.copy(alpha = BaseTokens.DisabledSecondaryContentAlpha)
                    )
                ) {
                    Text(label, style = labelTextStyle)
                }
            }

            ButtonVariant.Outline -> {
                OutlinedButton(
                    onClick = onClick,
                    enabled = enabled,
                    shape = shape,
                    contentPadding = buttonContentPaddingFor(variant, size),
                    modifier = modifier.height(buttonHeightFor(size)),
                    border = BorderStroke(BaseTokens.BorderWidth, colors.accent),
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = colors.accent,
                        disabledContentColor = colors.accent.copy(alpha = BaseTokens.DisabledSecondaryContentAlpha)
                    )
                ) {
                    Text(label, style = labelTextStyle)
                }
            }

            ButtonVariant.Text,
            ButtonVariant.TextMuted -> {
                val textColor =
                    if (variant == ButtonVariant.Text) colors.accent else colors.textMuted
                TextButton(
                    onClick = onClick,
                    enabled = enabled,
                    shape = shape,
                    contentPadding = buttonContentPaddingFor(variant, size),
                    modifier = modifier.height(buttonHeightFor(size)),
                    colors = ButtonDefaults.textButtonColors(
                        contentColor = textColor,
                        disabledContentColor = textColor.copy(alpha = BaseTokens.DisabledTertiaryContentAlpha)
                    )
                ) {
                    Text(label, style = labelTextStyle)
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
        val colors = LocalColors.current
        val icon = atlasIcon(role)
        val iconSize = if (size == ButtonSize.Regular) {
            BaseTokens.IconPrimarySize
        } else {
            BaseTokens.IconCompactSize
        }

        when (variant) {
            IconButtonVariant.Primary -> {
                val shadowModifier = if (enabled) {
                    Modifier.primaryIconButtonShadow(colors.primary.copy(alpha = BaseTokens.PrimaryShadowAlpha))
                } else {
                    Modifier
                }
                FilledIconButton(
                    onClick = onClick,
                    enabled = enabled,
                    shape = RoundedCornerShape(BaseTokens.ButtonCornerRadiusCompact),
                    modifier = Modifier
                        .size(iconButtonSizeFor(size))
                        .then(shadowModifier),
                    colors = IconButtonDefaults.filledIconButtonColors(
                        containerColor = colors.primary,
                        contentColor = Color.White,
                        disabledContainerColor = colors.primary.copy(alpha = BaseTokens.DisabledPrimaryContainerAlpha),
                        disabledContentColor = Color.White.copy(alpha = BaseTokens.DisabledPrimaryContentAlpha)
                    )
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = role.javaClass.simpleName,
                        modifier = Modifier.size(iconSize)
                    )
                }
            }

            IconButtonVariant.Secondary -> {
                FilledIconButton(
                    onClick = onClick,
                    enabled = enabled,
                    shape = RoundedCornerShape(BaseTokens.ButtonCornerRadiusCompact),
                    modifier = Modifier.size(iconButtonSizeFor(size)),
                    colors = IconButtonDefaults.filledIconButtonColors(
                        containerColor = colors.cardBg,
                        contentColor = colors.text,
                        disabledContainerColor = colors.cardBg,
                        disabledContentColor = colors.text.copy(alpha = BaseTokens.DisabledSecondaryContentAlpha)
                    )
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = role.javaClass.simpleName,
                        modifier = Modifier.size(iconSize)
                    )
                }
            }

            IconButtonVariant.Outlined -> {
                OutlinedIconButton(
                    onClick = onClick,
                    enabled = enabled,
                    shape = RoundedCornerShape(BaseTokens.ButtonCornerRadiusCompact),
                    modifier = Modifier.size(iconButtonSizeFor(size)),
                    border = BorderStroke(BaseTokens.BorderWidth, colors.btnBorder),
                    colors = IconButtonDefaults.outlinedIconButtonColors(
                        contentColor = colors.text,
                        disabledContentColor = colors.text.copy(alpha = BaseTokens.DisabledSecondaryContentAlpha)
                    )
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = role.javaClass.simpleName,
                        modifier = Modifier.size(iconSize)
                    )
                }
            }

            IconButtonVariant.Colored -> {
                val color = tintColor ?: colors.accent
                OutlinedIconButton(
                    onClick = onClick,
                    enabled = enabled,
                    shape = RoundedCornerShape(BaseTokens.ButtonCornerRadiusCompact),
                    modifier = Modifier.size(iconButtonSizeFor(size)),
                    border = BorderStroke(BaseTokens.BorderWidth, colors.btnBorder),
                    colors = IconButtonDefaults.outlinedIconButtonColors(
                        contentColor = color,
                        disabledContentColor = color.copy(alpha = BaseTokens.DisabledSecondaryContentAlpha)
                    )
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = role.javaClass.simpleName,
                        modifier = Modifier.size(iconSize)
                    )
                }
            }
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
        val active = style == CardStyle.Active
        val tappable = style == CardStyle.Tappable

        val container = if (active) {
            colors.accent.copy(alpha = BaseTokens.ActiveCardAlpha)
        } else {
            colors.cardBg
        }
        val border = if (active) {
            BorderStroke(
                BaseTokens.ActiveCardBorderWidth,
                colors.accent.copy(alpha = BaseTokens.ActiveCardBorderAlpha)
            )
        } else if (tappable) {
            BorderStroke(BaseTokens.BorderWidth, colors.cardBg.tappableCardBorderColor())
        } else {
            null
        }
        if (tappable) {
            Card(
                onClick = onClick ?: {},
                enabled = enabled,
                interactionSource = remember { MutableInteractionSource() },
                modifier = modifier,
                shape = cardShape,
                colors = CardDefaults.cardColors(
                    containerColor = container,
                    contentColor = colors.text,
                    disabledContainerColor = container,
                    disabledContentColor = colors.textMuted
                ),
                border = border
            ) {
                Box(modifier = Modifier.padding(contentPadding)) { content() }
            }
        } else {
            Card(
                modifier = modifier,
                shape = cardShape,
                colors = CardDefaults.cardColors(
                    containerColor = container,
                    contentColor = colors.text,
                    disabledContainerColor = container,
                    disabledContentColor = colors.textMuted
                ),
                border = border
            ) {
                Box(modifier = Modifier.padding(contentPadding)) { content() }
            }
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
    override fun AppLogoBadge(
        modifier: Modifier,
        badgeSize: Dp
    ) {
        val colors = LocalColors.current
        val context = LocalContext.current
        val iconResId = remember(context) {
            context.resources.getIdentifier(
                LauncherIconResName,
                "drawable",
                context.packageName
            )
        }
        val iconSize = badgeSize * 0.8f
        val cornerRadius = badgeSize / 4
        val shape = RoundedCornerShape(cornerRadius)
        val shadowColor = Color.Black

        Box(
            modifier = modifier
                .size(badgeSize)
                .shadow(
                    elevation = 8.dp,
                    shape = shape,
                    spotColor = shadowColor.copy(alpha = 0.8f)
                )
                .background(
                    color = colors.secondaryCardBg,
                    shape = shape
                ),
            contentAlignment = Alignment.Center
        ) {
            if (iconResId != 0) {
                val painter = painterResource(id = iconResId)
                Box(
                    modifier = Modifier
                        .offset(y = BaseTokens.IconPrimaryShadowYOffset * 2f)
                        .blur(10.dp)
                        .alpha(0.55f),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painter,
                        contentDescription = null,
                        tint = colors.text,
                        modifier = Modifier.size(iconSize)
                    )
                }
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        painter = painter,
                        contentDescription = null,
                        tint = colors.text,
                        modifier = Modifier.size(iconSize)
                    )
                }
            }
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
                checkedBorderColor = colors.primary,
                uncheckedThumbColor = colors.btnText,
                uncheckedTrackColor = colors.dotInactive,
                uncheckedBorderColor = colors.primary,
                disabledCheckedTrackColor = colors.primary.copy(alpha = BaseTokens.DisabledTrackAlpha),
                disabledCheckedThumbColor = colors.bg.copy(alpha = BaseTokens.DisabledPrimaryContentAlpha),
                disabledCheckedBorderColor = colors.primary.copy(alpha = BaseTokens.DisabledTrackAlpha),
                disabledUncheckedThumbColor = colors.btnText.copy(alpha = BaseTokens.DisabledSecondaryContentAlpha),
                disabledUncheckedTrackColor = colors.dotInactive.copy(alpha = BaseTokens.DisabledTrackAlpha),
                disabledUncheckedBorderColor = colors.primary.copy(alpha = BaseTokens.DisabledTrackAlpha)
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
                disabledThumbColor = colors.primary.copy(alpha = BaseTokens.DisabledTrackAlpha),
                disabledActiveTrackColor = colors.primary.copy(alpha = BaseTokens.DisabledTrackAlpha),
                disabledActiveTickColor = colors.bg.copy(alpha = BaseTokens.DisabledPrimaryContentAlpha),
                disabledInactiveTrackColor = colors.dotInactive.copy(alpha = BaseTokens.DisabledInactiveTrackAlpha),
                disabledInactiveTickColor = colors.dotInactive.copy(alpha = BaseTokens.DisabledInactiveTrackAlpha)
            )
        )
    }

    @Composable
    override fun Divider() {
        val colors = LocalColors.current
        HorizontalDivider(
            color = colors.text.copy(alpha = BaseTokens.DividerAlpha),
            thickness = 1.dp
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
        val typography = LocalAtlasTypography.current
        val confirmAction = onConfirm ?: onDismiss

        Dialog(
            onDismissRequest = onDismiss,
            properties = DialogProperties(usePlatformDefaultWidth = false)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(
                        horizontal = BaseTokens.AlertBackdropHorizontalPadding,
                        vertical = BaseTokens.AlertBackdropVerticalPadding
                    )
                    .background(
                        color = Color(0xFF1A1A1A).copy(alpha = BaseTokens.DialogBackdropAlpha),
                        shape = RoundedCornerShape(BaseTokens.AlertBackdropCornerRadius)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .widthIn(max = BaseTokens.AlertMaxWidth)
                        .graphicsLayer {
                            shadowElevation = BaseTokens.AlertShadowElevation.toPx()
                            shape = RoundedCornerShape(BaseTokens.AlertCornerRadius)
                            clip = false
                            ambientShadowColor =
                                Color.Black.copy(alpha = BaseTokens.AlertShadowAlpha)
                            spotShadowColor =
                                Color.Black.copy(alpha = BaseTokens.AlertShadowAlpha)
                        },
                    shape = RoundedCornerShape(BaseTokens.AlertCornerRadius),
                    colors = CardDefaults.cardColors(containerColor = colors.bg)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                start = BaseTokens.AlertHorizontalPadding,
                                end = BaseTokens.AlertHorizontalPadding,
                                top = BaseTokens.AlertTopPadding,
                                bottom = BaseTokens.AlertBottomPadding
                            ),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = title,
                            style = typography.dialogTitleStyle(),
                            color = colors.text,
                            textAlign = TextAlign.Center
                        )
                        Text(
                            text = text,
                            modifier = Modifier.padding(top = 6.dp, bottom = 24.dp),
                            style = typography.dialogBodyStyle(),
                            color = colors.textMuted,
                            textAlign = TextAlign.Center
                        )
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(BaseTokens.AlertActionSpacing)
                        ) {
                            if (dismissLabel != null) {
                                OutlinedButton(
                                    onClick = onDismiss,
                                    shape = RoundedCornerShape(BaseTokens.ButtonCornerRadiusCompact),
                                    modifier = Modifier
                                        .weight(1f)
                                        .height(BaseTokens.AlertActionHeight),
                                    border = BorderStroke(
                                        BaseTokens.BorderWidth,
                                        colors.btnBorder
                                    ),
                                    colors = ButtonDefaults.outlinedButtonColors(contentColor = colors.text)
                                ) {
                                    Text(
                                        text = dismissLabel,
                                        style = typography.dialogButtonStyle()
                                    )
                                }
                            }

                            Button(
                                onClick = confirmAction,
                                shape = RoundedCornerShape(BaseTokens.ButtonCornerRadiusCompact),
                                modifier = Modifier
                                    .weight(1f)
                                    .height(BaseTokens.AlertActionHeight),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = colors.primary,
                                    contentColor = Color.White
                                )
                            ) {
                                Text(
                                    text = confirmLabel,
                                    style = typography.dialogButtonStyle().copy(
                                        fontWeight = FontWeight.W600
                                    )
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    @Composable
    @OptIn(ExperimentalMaterial3Api::class)
    override fun BottomDrawer(
        onDismiss: () -> Unit,
        content: @Composable () -> Unit
    ) {
        val colors = LocalColors.current
        val bottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
        ModalBottomSheet(
            onDismissRequest = onDismiss,
            containerColor = colors.bg,
            sheetState = bottomSheetState
        ) {
            content()
        }
    }

    private fun buttonShapeFor(size: ButtonSize): RoundedCornerShape = RoundedCornerShape(
        if (size == ButtonSize.Regular) BaseTokens.ButtonCornerRadiusRegular else BaseTokens.ButtonCornerRadiusCompact
    )

    private fun buttonContentPaddingFor(variant: ButtonVariant, size: ButtonSize): PaddingValues =
        ComponentTokens.ButtonContentPadding

    private fun buttonHeightFor(size: ButtonSize) = when (size) {
        ButtonSize.Regular -> ComponentTokens.ButtonRegularHeight
        ButtonSize.Compact -> ComponentTokens.ButtonCompactHeight
    }

    private fun iconButtonSizeFor(size: ButtonSize) = when (size) {
        ButtonSize.Regular -> ComponentTokens.IconButtonRegularSize
        ButtonSize.Compact -> ComponentTokens.IconButtonCompactSize
    }


    private fun Modifier.primaryButtonShadow(
        shadowColor: Color,
        cornerRadius: Dp
    ): Modifier = graphicsLayer(clip = false).drawBehind {
        val radius = cornerRadius.toPx()
        val offsetY = BaseTokens.PrimaryShadowYOffset.toPx()
        val blurPx = BaseTokens.PrimaryShadowElevation.toPx()

        drawIntoCanvas { canvas ->
            val paint = AndroidPaint().apply {
                isAntiAlias = true
                style = AndroidPaint.Style.FILL
                color = shadowColor.toArgb()
                maskFilter = BlurMaskFilter(blurPx, BlurMaskFilter.Blur.NORMAL)
            }

            canvas.nativeCanvas.drawRoundRect(
                0f,
                offsetY,
                size.width,
                size.height + offsetY,
                radius,
                radius,
                paint
            )

            paint.color = shadowColor.copy(alpha = BaseTokens.PrimaryShadowAlpha * 0.7f).toArgb()
            paint.maskFilter = BlurMaskFilter(blurPx * 1.8f, BlurMaskFilter.Blur.NORMAL)
            canvas.nativeCanvas.drawRoundRect(
                0f,
                offsetY,
                size.width,
                size.height + offsetY,
                radius,
                radius,
                paint
            )

            paint.maskFilter = null
        }
    }


    private fun Modifier.primaryIconButtonShadow(shadowColor: Color): Modifier = graphicsLayer {
        clip = false
    }.drawBehind {
        val radius = BaseTokens.ButtonCornerRadiusCompact.toPx()
        val offsetY = BaseTokens.IconPrimaryShadowYOffset.toPx()
        val blurPx = BaseTokens.IconPrimaryShadowElevation.toPx()

        drawIntoCanvas { canvas ->
            val paint = AndroidPaint().apply {
                isAntiAlias = true
                style = AndroidPaint.Style.FILL
                color = shadowColor.toArgb()
                maskFilter = BlurMaskFilter(blurPx, BlurMaskFilter.Blur.NORMAL)
            }

            canvas.nativeCanvas.drawRoundRect(
                0f,
                offsetY,
                size.width,
                size.height + offsetY,
                radius,
                radius,
                paint
            )

            paint.color = shadowColor.copy(alpha = BaseTokens.PrimaryShadowAlpha * 0.7f).toArgb()
            paint.maskFilter = BlurMaskFilter(blurPx * 1.8f, BlurMaskFilter.Blur.NORMAL)
            canvas.nativeCanvas.drawRoundRect(
                0f,
                offsetY,
                size.width,
                size.height + offsetY,
                radius,
                radius,
                paint
            )

            paint.maskFilter = null
        }
    }
}

@Stable
fun Modifier.fadeTopEdge(
    fadeHeight: Dp = 12.dp
): Modifier = composed {
    if (fadeHeight <= 0.dp) return@composed this

    graphicsLayer(compositingStrategy = CompositingStrategy.Offscreen)
        .drawWithContent {
            drawContent()
            if (size.height <= 0f) return@drawWithContent
            val fadeStop = (fadeHeight.toPx() / size.height).coerceIn(0f, 1f)

            drawRect(
                brush = Brush.verticalGradient(
                    colorStops = arrayOf(
                        0f to Color.Transparent,
                        fadeStop to Color.Black,
                        1f to Color.Black
                    )
                ),
                blendMode = BlendMode.DstIn
            )
        }
}

object BaseComponents : BaseAtlasComponents()

private const val TAPPABLE_CARD_BORDER_ALPHA = 0.05f

private fun Color.tappableCardBorderColor(): Color =
    if (luminance() > 0.5f) {
        Color.Black.copy(alpha = TAPPABLE_CARD_BORDER_ALPHA)
    } else {
        Color.White.copy(alpha = TAPPABLE_CARD_BORDER_ALPHA)
    }
