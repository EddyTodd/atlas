package com.ynmidk.atlas.screens

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import com.ynmidk.atlas.theme.AtlasNavigation
import com.ynmidk.atlas.theme.LocalAtlasNavigation
import com.ynmidk.atlas.theme.LocalAtlasScreens
import com.ynmidk.atlas.theme.ThemeId
import com.ynmidk.atlas.theme.ThemeSelection

enum class AtlasRoute {
    Home,
    Game,
    SettingsMain,
    GameplaySettings,
    HowToPlay,
    RemoveAds,
    MoreGames,
    Language,
    About,
    Terms,
    Privacy,
    Licenses,
    Achievements,
    Statistics,
    Theme
}

@Composable
fun AtlasHost(
    continueDescription: String?,
    onStartGame: () -> Unit,
    newGameOptionsContent: (@Composable () -> Unit)?,
    gameplayView: @Composable () -> Unit,
    themeSelection: ThemeSelection,
    systemInDarkTheme: Boolean,
    onThemeSelectionChange: (ThemeSelection) -> Unit,
    themePreview: @Composable (themeId: ThemeId, onPreviewTap: () -> Unit) -> Unit,
    gameplaySettingsScreen: @Composable (onBack: () -> Unit) -> Unit,
    howToPlayScreen: @Composable (onBack: () -> Unit) -> Unit,
    statisticsScreen: @Composable (onBack: () -> Unit) -> Unit,
    achievementsScreen: @Composable (onBack: () -> Unit) -> Unit,
    languageScreen: @Composable (onBack: () -> Unit) -> Unit,
    removeAdsScreen: @Composable (onBack: () -> Unit) -> Unit,
    moreGamesScreen: @Composable (onBack: () -> Unit) -> Unit,
    termsScreen: (@Composable (onBack: () -> Unit) -> Unit)? = null,
    privacyScreen: (@Composable (onBack: () -> Unit) -> Unit)? = null,
    licensesScreen: (@Composable (onBack: () -> Unit) -> Unit)? = null,
    onRouteChanged: (AtlasRoute) -> Unit = {}
) {
    val screens = LocalAtlasScreens.current
    val context = LocalContext.current
    val activity = context as? Activity
    val backStack = remember { mutableStateListOf(AtlasRoute.Home) }
    val route = backStack.last()

    fun navigateTo(target: AtlasRoute) {
        if (backStack.last() != target) {
            backStack.add(target)
        }
    }

    fun popRoute(): Boolean {
        if (backStack.size > 1) {
            backStack.removeAt(backStack.lastIndex)
            return true
        }
        return false
    }

    val onNavigateBack: () -> Unit = {
        if (!popRoute()) {
            activity?.finish()
        }
    }

    LaunchedEffect(route) {
        onRouteChanged(route)
    }

    val navigation = remember(route) {
        AtlasNavigation(
            onNavigateBack = onNavigateBack,
            onOpenSettings = { navigateTo(AtlasRoute.SettingsMain) },
            onOpenGameplaySettings = { navigateTo(AtlasRoute.GameplaySettings) },
            onOpenHowToPlay = { navigateTo(AtlasRoute.HowToPlay) },
            onOpenStatistics = { navigateTo(AtlasRoute.Statistics) },
            onOpenAchievements = { navigateTo(AtlasRoute.Achievements) },
            onOpenTheme = { navigateTo(AtlasRoute.Theme) },
            onOpenLanguage = { navigateTo(AtlasRoute.Language) },
            onOpenRemoveAds = { navigateTo(AtlasRoute.RemoveAds) },
            onOpenMoreGames = { navigateTo(AtlasRoute.MoreGames) },
            onOpenAbout = { navigateTo(AtlasRoute.About) }
        )
    }

    BackHandler(enabled = true, onBack = onNavigateBack)

    CompositionLocalProvider(LocalAtlasNavigation provides navigation) {
        when (route) {
            AtlasRoute.Home -> screens.HomeScreen(
                continueDescription = continueDescription,
                onContinue = if (continueDescription != null) {
                    { navigateTo(AtlasRoute.Game) }
                } else {
                    null
                },
                newGameOptionsContent = newGameOptionsContent,
                onStartGame = {
                    onStartGame()
                    navigateTo(AtlasRoute.Game)
                }
            )

            AtlasRoute.Game -> screens.GameScreen(
                gameplayView = gameplayView
            )

            AtlasRoute.SettingsMain -> screens.SettingsMainScreen()

            AtlasRoute.GameplaySettings -> gameplaySettingsScreen(onNavigateBack)

            AtlasRoute.HowToPlay -> howToPlayScreen(onNavigateBack)

            AtlasRoute.RemoveAds -> removeAdsScreen(onNavigateBack)

            AtlasRoute.MoreGames -> moreGamesScreen(onNavigateBack)

            AtlasRoute.Language -> languageScreen(onNavigateBack)

            AtlasRoute.About -> screens.AboutScreen(
                onBack = onNavigateBack,
                onOpenTermsOfService = { navigateTo(AtlasRoute.Terms) },
                onOpenPrivacyPolicy = { navigateTo(AtlasRoute.Privacy) },
                onOpenLicenses = { navigateTo(AtlasRoute.Licenses) }
            )

            AtlasRoute.Terms -> (termsScreen
                ?: { back -> screens.TermsOfServiceScreen(onBack = back) })(onNavigateBack)

            AtlasRoute.Privacy -> (privacyScreen
                ?: { back -> screens.PrivacyPolicyScreen(onBack = back) })(onNavigateBack)

            AtlasRoute.Licenses -> (licensesScreen
                ?: { back -> screens.LicensesScreen(onBack = back) })(onNavigateBack)

            AtlasRoute.Achievements -> achievementsScreen(onNavigateBack)

            AtlasRoute.Statistics -> statisticsScreen(onNavigateBack)

            AtlasRoute.Theme -> ThemeScreen(
                themeSelection = themeSelection,
                systemInDarkTheme = systemInDarkTheme,
                onBack = onNavigateBack,
                onThemeSelectionChange = onThemeSelectionChange,
                preview = themePreview
            )
        }
    }
}
