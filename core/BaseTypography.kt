package com.ynmidk.atlas.core

import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontVariation
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import com.ynmidk.atlas.R
import com.ynmidk.atlas.theme.AtlasTextStyle

@OptIn(ExperimentalTextApi::class)
internal val DefaultTitleFontFamily = FontFamily(
    Font(
        resId = R.font.playfair_display,
        weight = FontWeight.W700,
        variationSettings = FontVariation.Settings(
            FontVariation.weight(700)
        )
    ),
    Font(
        resId = R.font.playfair_display,
        weight = FontWeight.W900,
        variationSettings = FontVariation.Settings(
            FontVariation.weight(900)
        )
    )
)

@OptIn(ExperimentalTextApi::class)
internal val DefaultBodyFontFamily = FontFamily(
    Font(
        resId = R.font.dm_sans,
        weight = FontWeight.W300,
        variationSettings = FontVariation.Settings(
            FontVariation.weight(300)
        )
    ),
    Font(
        resId = R.font.dm_sans,
        weight = FontWeight.W400,
        variationSettings = FontVariation.Settings(
            FontVariation.weight(400)
        )
    ),
    Font(
        resId = R.font.dm_sans,
        weight = FontWeight.W500,
        variationSettings = FontVariation.Settings(
            FontVariation.weight(500)
        )
    ),
    Font(
        resId = R.font.dm_sans,
        weight = FontWeight.W600,
        variationSettings = FontVariation.Settings(
            FontVariation.weight(600)
        )
    ),
    Font(
        resId = R.font.dm_sans,
        weight = FontWeight.W700,
        variationSettings = FontVariation.Settings(
            FontVariation.weight(700)
        )
    )
)

object BaseTypography : AtlasTypography() {

    override fun textStyle(style: AtlasTextStyle): TextStyle = when (style) {
        AtlasTextStyle.DisplayTitle -> TextStyle(
            fontFamily = DefaultTitleFontFamily,
            fontSize = ComponentTokens.DisplayTitleSize,
            lineHeight = BaseTokens.DisplayTitleLineHeight,
            fontWeight = FontWeight.W900,
            letterSpacing = BaseTokens.DisplayTitleLetterSpacing
        )

        AtlasTextStyle.Title -> TextStyle(
            fontFamily = DefaultTitleFontFamily,
            fontSize = ComponentTokens.TitleSize,
            lineHeight = BaseTokens.TitleLineHeight,
            fontWeight = FontWeight.W700,
            letterSpacing = BaseTokens.TitleLetterSpacing
        )

        AtlasTextStyle.Subtitle -> TextStyle(
            fontFamily = DefaultBodyFontFamily,
            fontSize = ComponentTokens.SubtitleSize,
            fontWeight = FontWeight.W700,
            letterSpacing = BaseTokens.SubtitleLetterSpacing
        )

        AtlasTextStyle.SectionTitle -> TextStyle(
            fontFamily = DefaultBodyFontFamily,
            fontSize = ComponentTokens.SectionTitleSize,
            fontWeight = FontWeight.W600,
            letterSpacing = BaseTokens.SubtitleLetterSpacing
        )

        AtlasTextStyle.CardTitle -> TextStyle(
            fontFamily = DefaultBodyFontFamily,
            fontSize = ComponentTokens.CardTitleSize,
            lineHeight = ComponentTokens.CardTitleSize,
            fontWeight = FontWeight.W700
        )

        AtlasTextStyle.CardSubtitle -> TextStyle(
            fontFamily = DefaultBodyFontFamily,
            fontSize = ComponentTokens.CardSubtitleSize,
            lineHeight = ComponentTokens.CardSubtitleSize,
            fontWeight = FontWeight.W500
        )

        AtlasTextStyle.Body -> TextStyle(
            fontFamily = DefaultBodyFontFamily,
            fontSize = ComponentTokens.BodySize,
            lineHeight = ComponentTokens.BodyLineHeight,
            fontWeight = FontWeight.W400
        )

        AtlasTextStyle.BodyStrong -> TextStyle(
            fontFamily = DefaultBodyFontFamily,
            fontSize = ComponentTokens.BodySize,
            lineHeight = ComponentTokens.BodyLineHeight,
            fontWeight = FontWeight.W600
        )

        AtlasTextStyle.Label -> TextStyle(
            fontFamily = DefaultBodyFontFamily,
            fontSize = ComponentTokens.LabelSize,
            fontWeight = FontWeight.W600,
            letterSpacing = BaseTokens.SubtitleLetterSpacing
        )

        AtlasTextStyle.Muted -> TextStyle(
            fontFamily = DefaultBodyFontFamily,
            fontSize = ComponentTokens.MutedSize,
            lineHeight = ComponentTokens.BodyLineHeight,
            fontWeight = FontWeight.W400
        )

        AtlasTextStyle.Caption -> TextStyle(
            fontFamily = DefaultBodyFontFamily,
            fontSize = ComponentTokens.CaptionSize,
            fontWeight = FontWeight.W400
        )

        AtlasTextStyle.Overline -> TextStyle(
            fontFamily = DefaultBodyFontFamily,
            fontSize = ComponentTokens.OverlineSize,
            fontWeight = FontWeight.W600,
            letterSpacing = BaseTokens.SubtitleLetterSpacing,
            textDecoration = TextDecoration.None
        )
    }

    override fun buttonTextStyle(variant: ButtonVariant, size: ButtonSize): TextStyle {
        val baseSize = when (size) {
            ButtonSize.Regular -> ComponentTokens.ButtonRegularLabelSize
            ButtonSize.Compact -> ComponentTokens.ButtonCompactLabelSize
        }
        return when (variant) {
            ButtonVariant.Primary -> TextStyle(
                fontFamily = DefaultBodyFontFamily,
                fontSize = baseSize,
                fontWeight = FontWeight.W600,
                letterSpacing = BaseTokens.ButtonLetterSpacing
            )

            ButtonVariant.Secondary,
            ButtonVariant.Outline -> TextStyle(
                fontFamily = DefaultBodyFontFamily,
                fontSize = baseSize,
                fontWeight = FontWeight.W500,
                letterSpacing = BaseTokens.ButtonLetterSpacing
            )

            ButtonVariant.Text,
            ButtonVariant.TextMuted -> TextStyle(
                fontFamily = DefaultBodyFontFamily,
                fontSize = ComponentTokens.TextButtonLabelSize,
                fontWeight = FontWeight.W500,
                letterSpacing = BaseTokens.ButtonLetterSpacing
            )
        }
    }

    override fun tabTextStyle(): TextStyle =
        TextStyle(fontSize = BaseTokens.TabLabelSize, fontWeight = FontWeight.Medium)

    override fun dialogTitleStyle(): TextStyle = TextStyle(
        fontFamily = DefaultTitleFontFamily,
        fontWeight = FontWeight.W700,
        fontSize = ComponentTokens.DialogTitleSize
    )

    override fun dialogBodyStyle(): TextStyle = TextStyle(
        fontFamily = DefaultBodyFontFamily,
        fontWeight = FontWeight.W400,
        fontSize = ComponentTokens.DialogBodySize,
        lineHeight = ComponentTokens.DialogBodyLineHeight
    )

    override fun dialogButtonStyle(): TextStyle = TextStyle(
        fontFamily = DefaultBodyFontFamily,
        fontWeight = FontWeight.W500,
        fontSize = ComponentTokens.DialogButtonLabelSize,
        letterSpacing = BaseTokens.ButtonLetterSpacing
    )
}
