package com.ynmidk.atlas.core

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.rounded.KeyboardArrowRight
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.ui.graphics.vector.ImageVector

interface AtlasIconSet {
    fun icon(role: IconRole): ImageVector
}

object DefaultAtlasIcons : AtlasIconSet {
    override fun icon(role: IconRole): ImageVector = when (role) {
        IconRole.Back -> Icons.AutoMirrored.Rounded.KeyboardArrowLeft
        IconRole.Settings -> Icons.Rounded.Settings
        IconRole.ChevronRight -> Icons.AutoMirrored.Rounded.KeyboardArrowRight
    }
}

fun atlasIcon(role: IconRole): ImageVector = DefaultAtlasIcons.icon(role)
