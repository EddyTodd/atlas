package com.ynmidk.atlas.core

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ynmidk.atlas.core.ButtonSize

internal object BaseTokens {
    // Shapes & layout
    val CardCornerRadius = 16.dp
    val ButtonCornerRadiusRegular = 16.dp
    val ButtonCornerRadiusCompact = 12.dp
    val AlertCornerRadius = 20.dp
    val BorderWidth = 1.5.dp
    val ActiveCardBorderWidth = 2.dp
    val CardPadding = 18.dp
    val TopBarHeight = 80.dp

    // Buttons / icon buttons
    val ButtonRegularHorizontalPadding = 32.dp
    val ButtonRegularVerticalPadding = 16.dp
    val ButtonCompactHorizontalPadding = 16.dp
    val ButtonCompactVerticalPadding = 10.dp
    val TextButtonHorizontalPadding = 16.dp
    val TextButtonVerticalPadding = 10.dp
    val IconButtonSize = 48.dp
    val IconButtonCompactSize = 36.dp
    val IconPrimarySize = 28.dp
    val IconSecondarySize = 24.dp
    val IconOutlinedSize = 24.dp
    val IconColoredSize = 24.dp
    val IconCompactSize = 20.dp

    // Typography
    val DisplayTitleLineHeight = 44.sp
    val DisplayTitleLetterSpacing = (-1.5).sp
    val TitleLineHeight = 32.sp
    val TitleLetterSpacing = (-0.5).sp
    val SubtitleLetterSpacing = 0.5.sp
    val ButtonLetterSpacing = 0.3.sp
    val TabLabelSize = 14.sp

    // Alert dialog
    val AlertBackdropCornerRadius = 20.dp
    val AlertBackdropHorizontalPadding = 28.dp
    val AlertBackdropVerticalPadding = 60.dp
    val AlertMaxWidth = 320.dp
    val AlertTopPadding = 28.dp
    val AlertHorizontalPadding = 24.dp
    val AlertBottomPadding = 20.dp
    val AlertActionSpacing = 10.dp
    val AlertActionHeight = 42.dp
    val AlertShadowElevation = 16.dp

    // Disabled/alpha values
    const val PrimaryShadowAlpha = 0.16f
    const val SecondaryPressedAlpha = 0.95f
    const val TextPressedAlpha = 0.7f
    const val DisabledPrimaryContainerAlpha = 0.35f
    const val DisabledPrimaryContentAlpha = 0.6f
    const val DisabledSecondaryContentAlpha = 0.5f
    const val DisabledTertiaryContentAlpha = 0.45f
    const val DisabledTrackAlpha = 0.35f
    const val DisabledInactiveTrackAlpha = 0.25f
    const val ActiveCardAlpha = 0.08f
    const val ActiveCardBorderAlpha = 0.35f
    const val DialogBackdropAlpha = 0.3f
    const val DividerAlpha = 0.08f
    const val AlertShadowAlpha = 0.2f

    // Shadow
    val PrimaryShadowElevation = 8.dp
    val PrimaryShadowYOffset = 2.dp
    val IconPrimaryShadowElevation = 10.dp
    val IconPrimaryShadowYOffset = 2.dp
}

object ComponentTokens {
    val ButtonRegularHeight = 60.dp
    val ButtonCompactHeight = 40.dp
    val ButtonContentPadding = PaddingValues(horizontal = 16.dp, vertical = 10.dp)
    val PrimaryButtonTextPaddingRegular = 30.dp
    val PrimaryButtonTextPaddingCompact = 16.dp
    val ButtonRegularLabelSize = 18.sp
    val ButtonCompactLabelSize = 14.sp
    val TextButtonLabelSize = 14.sp

    val IconButtonRegularSize = 48.dp
    val IconButtonCompactSize = 36.dp

    val DisplayTitleSize = 44.sp
    val TitleSize = 32.sp
    val SubtitleSize = 16.sp
    val SectionTitleSize = 20.sp
    val CardTitleSize = 14.sp
    val CardSubtitleSize = 12.sp
    val CardBodySize = 12.sp
    val BodySize = 14.sp
    val BodyLineHeight = 22.5.sp
    val LabelSize = 13.sp
    val MutedSize = 14.sp
    val CaptionSize = 12.sp
    val OverlineSize = 11.sp

    val DialogTitleSize = 22.sp
    val DialogBodySize = 14.sp
    val DialogBodyLineHeight = 21.sp
    val DialogButtonLabelSize = 14.sp
}

internal fun primaryButtonTextPadding(size: ButtonSize) =
    if (size == ButtonSize.Regular) ComponentTokens.PrimaryButtonTextPaddingRegular else ComponentTokens.PrimaryButtonTextPaddingCompact
