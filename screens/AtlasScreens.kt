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
    val title: String
)

data class AchievementItem(
    val id: String,
    val icon: String,
    val name: String,
    val description: String,
    val unlocked: Boolean,
    val status: String? = null
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
        continueDescription: String?,
        onContinue: (() -> Unit)?,
        newGameOptionsContent: (@Composable () -> Unit)? = null,
        onStartGame: () -> Unit
    )

    @Composable
    fun GameScreen(
        showAdBanner: Boolean = true,
        gameplayView: @Composable () -> Unit
    )

    @Composable
    fun SettingsMainScreen()

    @Composable
    fun AboutScreen(
        title: String? = null,
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
        highContrastEnabled: Boolean,
        onToggleHighContrast: (Boolean) -> Unit,
        onSelectTheme: (ThemeOption) -> Unit,
        onBack: (() -> Unit)? = null
    )
}
