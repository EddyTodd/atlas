package com.ynmidk.atlas.screens

import android.content.Context
import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import com.ynmidk.atlas.game.TicTacToeGameState
import com.ynmidk.atlas.game.TicTacToeGameStore
import com.ynmidk.atlas.theme.LocalAtlasComponents
import com.ynmidk.atlas.theme.LocalAtlasScreens
import com.ynmidk.atlas.theme.ThemeId
import kotlinx.coroutines.launch

private enum class GameRoute {
    Home,
    Game,
    SettingsMain,
    GameplaySettings,
    HowToPlay,
    RemoveAds,
    MoreGames,
    Language,
    About,
    TermsOfService,
    PrivacyPolicy,
    Licenses,
    Achievements,
    Statistics,
    Theme
}

@Composable
fun AtlasGameScreen(
    themeId: ThemeId,
    onThemeChange: (ThemeId) -> Unit,
    themePreview: @Composable (themeId: ThemeId, onThemeChange: (ThemeId) -> Unit) -> Unit,
    homeDisplayTitle: String? = null
) {
    val c = LocalAtlasComponents.current
    val screens = LocalAtlasScreens.current
    val context = LocalContext.current
    val appTitle = remember(context) { resolveAppTitle(context) }
    val gameStore = remember(context) {
        TicTacToeGameStore(context.applicationContext)
    }
    val scope = rememberCoroutineScope()
    val gameState by gameStore.gameStateFlow.collectAsState(initial = TicTacToeGameState.fresh())

    var route by remember { mutableStateOf(GameRoute.Home) }
    var showNewGameDialog by remember { mutableStateOf(false) }

    val startNewGame: () -> Unit = {
        scope.launch {
            gameStore.startNewGame()
            route = GameRoute.Game
        }
    }

    val navigateBack: () -> Unit = {
        route = when (route) {
            GameRoute.Home -> GameRoute.Home
            GameRoute.Game,
            GameRoute.SettingsMain -> GameRoute.Home

            GameRoute.GameplaySettings,
            GameRoute.HowToPlay,
            GameRoute.RemoveAds,
            GameRoute.MoreGames,
            GameRoute.Language,
            GameRoute.About,
            GameRoute.Achievements,
            GameRoute.Statistics,
            GameRoute.Theme -> GameRoute.SettingsMain

            GameRoute.TermsOfService,
            GameRoute.PrivacyPolicy,
            GameRoute.Licenses -> GameRoute.About
        }
    }

    BackHandler(enabled = route != GameRoute.Home) {
        navigateBack()
    }

    when (route) {
        GameRoute.Home -> screens.HomeScreen(
            displayTitle = homeDisplayTitle,
            subtitle = "Local two-player mode",
            difficulties = listOf(
                HomeDifficulty("local", "Local", "2 players")
            ),
            selectedDifficultyId = "local",
            onDifficultySelected = {},
            continueTitle = if (gameState.isInProgress) "Continue game" else null,
            continueSubtitle = if (gameState.isInProgress) {
                "Player ${gameState.currentPlayer.token} to move"
            } else {
                null
            },
            onContinue = if (gameState.isInProgress) {
                { route = GameRoute.Game }
            } else {
                null
            },
            onOpenSettings = { route = GameRoute.SettingsMain },
            onStartGame = {
                if (gameState.isInProgress) {
                    showNewGameDialog = true
                } else {
                    startNewGame()
                }
            }
        )

        GameRoute.Game -> TicTacToeGameScreen(
            appTitle = appTitle,
            gameState = gameState,
            onBack = { route = GameRoute.Home },
            onOpenSettings = { route = GameRoute.SettingsMain },
            onTapCell = { index ->
                scope.launch {
                    gameStore.playMove(index)
                }
            }
        )

        GameRoute.SettingsMain -> screens.SettingsMainScreen(
            title = "Settings",
            subtitle = "Customize your experience",
            sections = listOf(
                SettingsSection(
                    label = "Game",
                    items = listOf(
                        SettingsItem("gameplay", "🎮", "Gameplay", "Animations, haptics, gameplay features"),
                        SettingsItem("how_to_play", "❓", "How to Play", "Rules, controls, and tips"),
                        SettingsItem("statistics", "📊", "Statistics", "Win rate, best times, streaks"),
                        SettingsItem("achievements", "🏆", "Achievements", "1 of 2 unlocked")
                    )
                ),
                SettingsSection(
                    label = "Preferences",
                    items = listOf(
                        SettingsItem("theme", "🎨", "Theme", themeId.label),
                        SettingsItem("language", "🌐", "Language", "Choose app language")
                    )
                ),
                SettingsSection(
                    label = "Store",
                    items = listOf(
                        SettingsItem("remove_ads", "🚫", "Remove Ads", "Enjoy an ad-free experience"),
                        SettingsItem("more_games", "🕹️", "More Games", "Explore other titles")
                    )
                ),
                SettingsSection(
                    label = "Info",
                    items = listOf(
                        SettingsItem("about", "ℹ️", "About", "Version, credits")
                    )
                )
            ),
            onBack = navigateBack,
            onSelectItem = { item ->
                route = when (item.id) {
                    "gameplay" -> GameRoute.GameplaySettings
                    "how_to_play" -> GameRoute.HowToPlay
                    "statistics" -> GameRoute.Statistics
                    "achievements" -> GameRoute.Achievements
                    "theme" -> GameRoute.Theme
                    "language" -> GameRoute.Language
                    "remove_ads" -> GameRoute.RemoveAds
                    "more_games" -> GameRoute.MoreGames
                    "about" -> GameRoute.About
                    else -> GameRoute.SettingsMain
                }
            }
        )

        GameRoute.GameplaySettings -> GameplaySettingsScreen(
            onBack = navigateBack
        )

        GameRoute.HowToPlay -> HowToPlayScreen(
            onBack = navigateBack
        )

        GameRoute.RemoveAds -> RemoveAdsScreen(
            onBack = navigateBack
        )

        GameRoute.MoreGames -> MoreGamesScreen(
            onBack = navigateBack
        )

        GameRoute.Language -> LanguageScreen(
            onBack = navigateBack
        )

        GameRoute.About -> screens.AboutScreen(
            onBack = navigateBack,
            onOpenTermsOfService = { route = GameRoute.TermsOfService },
            onOpenPrivacyPolicy = { route = GameRoute.PrivacyPolicy },
            onOpenLicenses = { route = GameRoute.Licenses }
        )

        GameRoute.TermsOfService -> screens.TermsOfServiceScreen(
            onBack = navigateBack
        )

        GameRoute.PrivacyPolicy -> screens.PrivacyPolicyScreen(
            onBack = navigateBack
        )

        GameRoute.Licenses -> screens.LicensesScreen(
            onBack = navigateBack
        )

        GameRoute.Achievements -> screens.AchievementsScreen(
            summary = "1 of 2 unlocked",
            achievements = listOf(
                AchievementItem(
                    id = "first_win",
                    icon = "🎉",
                    name = "First Win",
                    description = "Complete your first game of tic-tac-toe",
                    unlocked = true,
                    status = "Unlocked Jan 15, 2026"
                ),
                AchievementItem(
                    id = "win_streak",
                    icon = "🔥",
                    name = "Win Streak",
                    description = "Win 5 games in a row",
                    unlocked = false,
                    status = "0 / 5"
                )
            ),
            onBack = navigateBack
        )

        GameRoute.Statistics -> screens.StatisticsScreen(
            stats = listOf(
                StatisticItem("Games Played", "47"),
                StatisticItem("Win Rate", "72%", highlighted = true),
                StatisticItem("Games Won", "34"),
                StatisticItem("Current Streak", "5"),
                StatisticItem("Best Streak", "12"),
                StatisticItem("Games Lost", "13")
            ),
            bestTimes = listOf(
                BestTimeItem("Easy", "0:42"),
                BestTimeItem("Medium", "2:18"),
                BestTimeItem("Hard", "—")
            ),
            onBack = navigateBack
        )

        GameRoute.Theme -> ThemeScreen(
            themeId = themeId,
            onBack = navigateBack,
            onThemeChange = onThemeChange,
            preview = themePreview
        )
    }

    if (showNewGameDialog) {
        c.AlertDialog(
            title = "Start a new game?",
            text = "Your current game will be replaced.",
            onDismiss = { showNewGameDialog = false },
            confirmLabel = "New Game",
            dismissLabel = "Cancel",
            onConfirm = {
                showNewGameDialog = false
                startNewGame()
            }
        )
    }
}

private fun resolveAppTitle(context: Context): String {
    val labelRes = context.applicationInfo.labelRes
    if (labelRes != 0) {
        return context.getString(labelRes)
    }
    return context.applicationInfo.loadLabel(context.packageManager).toString()
}
