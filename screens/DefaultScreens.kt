package com.ynmidk.atlas.screens

import androidx.compose.runtime.Composable

object DefaultScreens : AtlasScreens {
    @Composable
    override fun HomeScreen(
        continueDescription: String?,
        onContinue: (() -> Unit)?,
        newGameOptionsContent: (@Composable () -> Unit)?,
        onStartGame: () -> Unit
    ) = com.ynmidk.atlas.screens.HomeScreen(
        continueDescription = continueDescription,
        onContinue = onContinue,
        newGameOptionsContent = newGameOptionsContent,
        onStartGame = onStartGame
    )

    @Composable
    override fun GameScreen(
        showAdBanner: Boolean,
        gameplayView: @Composable () -> Unit
    ) = com.ynmidk.atlas.screens.GameScreen(
        showAdBanner = showAdBanner,
        gameplayView = gameplayView
    )

    @Composable
    override fun SettingsMainScreen() = SettingsScreen()

    @Composable
    override fun AboutScreen(
        title: String?,
        developer: String?,
        design: String,
        version: String?,
        platform: String?,
        footer: String?,
        onBack: (() -> Unit)?,
        onOpenTermsOfService: (() -> Unit)?,
        onOpenPrivacyPolicy: (() -> Unit)?,
        onOpenLicenses: (() -> Unit)?
    ) = DefaultAboutScreen(
        onBack = onBack,
        onOpenTermsOfService = onOpenTermsOfService,
        onOpenPrivacyPolicy = onOpenPrivacyPolicy,
        onOpenLicenses = onOpenLicenses
    )

    @Composable
    override fun TermsOfServiceScreen(onBack: (() -> Unit)?) =
        com.ynmidk.atlas.screens.TermsOfServiceScreen(
            onBack = onBack
        )

    @Composable
    override fun PrivacyPolicyScreen(onBack: (() -> Unit)?) =
        com.ynmidk.atlas.screens.PrivacyPolicyScreen(
            onBack = onBack
        )

    @Composable
    override fun LicensesScreen(onBack: (() -> Unit)?) = com.ynmidk.atlas.screens.LicensesScreen(
        onBack = onBack
    )

    @Composable
    override fun AchievementsScreen(
        achievements: List<AchievementItem>,
        onBack: (() -> Unit)?
    ) = DefaultAchievementsScreen(
        achievements = achievements,
        onBack = onBack
    )

    @Composable
    override fun StatisticsScreen(
        stats: List<StatisticItem>,
        bestTimes: List<BestTimeItem>,
        onBack: (() -> Unit)?
    ) = com.ynmidk.atlas.screens.StatisticsScreen(
        stats = stats,
        bestTimes = bestTimes,
        onBack = onBack
    )

    @Composable
    override fun ThemeScreen(
        options: List<ThemeOption>,
        selectedThemeId: String,
        highContrastEnabled: Boolean,
        onToggleHighContrast: (Boolean) -> Unit,
        onSelectTheme: (ThemeOption) -> Unit,
        onBack: (() -> Unit)?
    ) = DefaultThemeScreen(
        options = options,
        selectedThemeId = selectedThemeId,
        highContrastEnabled = highContrastEnabled,
        onToggleHighContrast = onToggleHighContrast,
        onSelectTheme = onSelectTheme,
        onBack = onBack
    )
}
