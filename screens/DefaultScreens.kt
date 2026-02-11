package com.ynmidk.atlas.screens

import androidx.compose.runtime.Composable

object DefaultScreens : AtlasScreens {
    @Composable
    override fun HomeScreen(
        displayTitle: String?,
        subtitle: String,
        difficulties: List<HomeDifficulty>,
        selectedDifficultyId: String,
        onDifficultySelected: (String) -> Unit,
        continueTitle: String?,
        continueSubtitle: String?,
        onContinue: (() -> Unit)?,
        onBack: (() -> Unit)?,
        onOpenSettings: (() -> Unit)?,
        onStartGame: () -> Unit
    ) = HomeScreen(
        displayTitle = displayTitle,
        continueTitle = continueTitle,
        continueSubtitle = continueSubtitle,
        onContinue = onContinue,
        onOpenSettings = onOpenSettings,
        onStartGame = onStartGame
    )

    @Composable
    override fun SettingsMainScreen(
        title: String,
        subtitle: String,
        sections: List<SettingsSection>,
        onBack: (() -> Unit)?,
        onSelectItem: (SettingsItem) -> Unit
    ) = SettingsScreen(
        title = title,
        sections = sections,
        onBack = onBack,
        onSelectItem = onSelectItem
    )

    @Composable
    override fun AboutScreen(
        appName: String?,
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
    override fun TermsOfServiceScreen(onBack: (() -> Unit)?) = com.ynmidk.atlas.screens.TermsOfServiceScreen(
        onBack = onBack
    )

    @Composable
    override fun PrivacyPolicyScreen(onBack: (() -> Unit)?) = com.ynmidk.atlas.screens.PrivacyPolicyScreen(
        onBack = onBack
    )

    @Composable
    override fun LicensesScreen(onBack: (() -> Unit)?) = com.ynmidk.atlas.screens.LicensesScreen(
        onBack = onBack
    )

    @Composable
    override fun AchievementsScreen(
        summary: String,
        achievements: List<AchievementItem>,
        onBack: (() -> Unit)?
    ) = DefaultAchievementsScreen(
        summary = summary,
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
        onSelectTheme: (ThemeOption) -> Unit,
        onBack: (() -> Unit)?
    ) = DefaultThemeScreen(
        options = options,
        selectedThemeId = selectedThemeId,
        onSelectTheme = onSelectTheme,
        onBack = onBack
    )
}
