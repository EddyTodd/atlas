package com.ynmidk.atlas.core

sealed interface IconRole {
    data object Back : IconRole
    data object Settings : IconRole
    data object ChevronRight : IconRole
}
