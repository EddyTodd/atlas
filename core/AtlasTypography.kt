package com.ynmidk.atlas.core

import androidx.compose.ui.text.TextStyle
import com.ynmidk.atlas.theme.AtlasTextStyle

open class AtlasTypography {

    open fun textStyle(style: AtlasTextStyle): TextStyle = TextStyle.Default

    open fun buttonTextStyle(variant: ButtonVariant, size: ButtonSize): TextStyle =
        TextStyle.Default

    open fun tabTextStyle(): TextStyle = TextStyle.Default

    open fun dialogTitleStyle(): TextStyle = TextStyle.Default

    open fun dialogBodyStyle(): TextStyle = TextStyle.Default

    open fun dialogButtonStyle(): TextStyle = TextStyle.Default
}
