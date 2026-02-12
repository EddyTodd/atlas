package com.ynmidk.atlas.theme

data class AtlasNavigation(
    val onNavigateBack: () -> Unit = {},
    val onOpenSettings: () -> Unit = {},
    val onOpenGameplaySettings: () -> Unit = {},
    val onOpenHowToPlay: () -> Unit = {},
    val onOpenStatistics: () -> Unit = {},
    val onOpenAchievements: () -> Unit = {},
    val onOpenTheme: () -> Unit = {},
    val onOpenLanguage: () -> Unit = {},
    val onOpenRemoveAds: () -> Unit = {},
    val onOpenMoreGames: () -> Unit = {},
    val onOpenAbout: () -> Unit = {}
)
