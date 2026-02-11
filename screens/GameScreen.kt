package com.ynmidk.atlas.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.ynmidk.atlas.core.CardStyle
import com.ynmidk.atlas.game.TicTacToeGameState
import com.ynmidk.atlas.theme.AtlasTextStyle
import com.ynmidk.atlas.theme.LocalAtlasComponents

@Composable
internal fun TicTacToeGameScreen(
    appTitle: String,
    gameState: TicTacToeGameState,
    onBack: () -> Unit,
    onOpenSettings: () -> Unit,
    onTapCell: (Int) -> Unit
) {
    val c = LocalAtlasComponents.current

    Scaffold(
        containerColor = Color.Transparent,
        topBar = {
            c.TopBar(
                title = appTitle,
                onLeadingIconClick = onBack,
                onTrailingIconClick = onOpenSettings
            )
        }
    ) { innerPadding ->
        c.ScreenBackground(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(horizontal = 20.dp, vertical = 24.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                c.Card(modifier = Modifier.fillMaxWidth()) {
                    c.Text(
                        text = gameHeadline(gameState),
                        style = AtlasTextStyle.CardTitle
                    )
                    c.Text(
                        text = gameSubline(gameState),
                        style = AtlasTextStyle.Caption
                    )
                }

                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    repeat(3) { row ->
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            repeat(3) { col ->
                                val cellIndex = row * 3 + col
                                val cellValue = gameState.board[cellIndex]?.token.orEmpty()
                                val isCellEnabled = !gameState.isFinished && gameState.board[cellIndex] == null

                                c.Card(
                                    modifier = Modifier
                                        .weight(1f)
                                        .aspectRatio(1f),
                                    style = if (isCellEnabled) CardStyle.Tappable else CardStyle.Regular,
                                    enabled = isCellEnabled,
                                    onClick = if (isCellEnabled) {
                                        { onTapCell(cellIndex) }
                                    } else {
                                        null
                                    }
                                ) {
                                    Box(
                                        modifier = Modifier.fillMaxSize(),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        c.Text(
                                            text = cellValue,
                                            style = AtlasTextStyle.DisplayTitle
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

private fun gameHeadline(state: TicTacToeGameState): String = when {
    state.winner != null -> "Player ${state.winner.token} wins"
    state.isDraw -> "It's a draw"
    else -> "Player ${state.currentPlayer.token}'s turn"
}

private fun gameSubline(state: TicTacToeGameState): String = when {
    state.winner != null || state.isDraw -> "Tap back to start a new game."
    else -> "First to complete a row wins."
}
