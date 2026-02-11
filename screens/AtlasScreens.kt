package com.ynmidk.atlas.screens

import androidx.compose.runtime.Composable

data class HomeDifficulty(
    val id: String,
    val name: String,
    val description: String
)

data class SettingsSection(
    val label: String,
    val items: List<SettingsItem>
)

data class SettingsItem(
    val id: String,
    val icon: String,
    val title: String,
    val subtitle: String
)

data class AchievementItem(
    val id: String,
    val icon: String,
    val name: String,
    val description: String,
    val unlocked: Boolean,
    val status: String
)

data class StatisticItem(
    val label: String,
    val value: String,
    val highlighted: Boolean = false
)

data class BestTimeItem(
    val difficulty: String,
    val time: String
)

data class ThemeOption(
    val id: String,
    val name: String,
    val subtitle: String,
    val preview: (@Composable () -> Unit)? = null
)

interface AtlasScreens {
    @Composable
    fun HomeScreen(
        displayTitle: String? = null,
        subtitle: String,
        difficulties: List<HomeDifficulty>,
        selectedDifficultyId: String,
        onDifficultySelected: (String) -> Unit,
        continueTitle: String?,
        continueSubtitle: String?,
        onContinue: (() -> Unit)?,
        onBack: (() -> Unit)? = null,
        onOpenSettings: (() -> Unit)? = null,
        onStartGame: () -> Unit
    )

    @Composable
    fun SettingsMainScreen(
        title: String,
        subtitle: String,
        sections: List<SettingsSection>,
        onBack: (() -> Unit)? = null,
        onSelectItem: (SettingsItem) -> Unit
    )

    @Composable
    fun AboutScreen(
        appName: String? = null,
        developer: String? = null,
        design: String = "Sandstone UI",
        version: String? = null,
        platform: String? = null,
        footer: String? = null,
        onBack: (() -> Unit)? = null,
        onOpenTermsOfService: (() -> Unit)? = null,
        onOpenPrivacyPolicy: (() -> Unit)? = null,
        onOpenLicenses: (() -> Unit)? = null
    )

    @Composable
    fun TermsOfServiceScreen(
        onBack: (() -> Unit)? = null
    )

    @Composable
    fun PrivacyPolicyScreen(
        onBack: (() -> Unit)? = null
    )

    @Composable
    fun LicensesScreen(
        onBack: (() -> Unit)? = null
    )

    @Composable
    fun AchievementsScreen(
        summary: String,
        achievements: List<AchievementItem>,
        onBack: (() -> Unit)? = null
    )

    @Composable
    fun StatisticsScreen(
        stats: List<StatisticItem>,
        bestTimes: List<BestTimeItem>,
        onBack: (() -> Unit)? = null
    )

    @Composable
    fun ThemeScreen(
        options: List<ThemeOption>,
        selectedThemeId: String,
        onSelectTheme: (ThemeOption) -> Unit,
        onBack: (() -> Unit)? = null
    )
}
