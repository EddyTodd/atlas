package com.ynmidk.atlas.game

import android.content.Context
import androidx.datastore.preferences.core.MutablePreferences
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.ticTacToeDataStore by preferencesDataStore(name = "atlas_tic_tac_toe")

class TicTacToeGameStore(
    private val context: Context
) {
    private val boardKey = stringPreferencesKey("board")
    private val currentPlayerKey = stringPreferencesKey("current_player")

    val gameStateFlow: Flow<TicTacToeGameState> = context.ticTacToeDataStore.data.map(::readState)

    suspend fun startNewGame() {
        context.ticTacToeDataStore.edit { prefs ->
            writeState(
                prefs = prefs,
                state = TicTacToeGameState.fresh()
            )
        }
    }

    suspend fun playMove(index: Int) {
        context.ticTacToeDataStore.edit { prefs ->
            val nextState = readState(prefs).withMove(index)
            writeState(
                prefs = prefs,
                state = nextState
            )
        }
    }

    private fun readState(prefs: Preferences): TicTacToeGameState {
        val board = decodeBoard(prefs[boardKey])
        val currentPlayer = TicTacToePlayer.fromToken(prefs[currentPlayerKey]) ?: TicTacToePlayer.X
        return TicTacToeGameState.fromPersisted(
            board = board,
            currentPlayer = currentPlayer
        )
    }

    private fun writeState(
        prefs: MutablePreferences,
        state: TicTacToeGameState
    ) {
        prefs[boardKey] = encodeBoard(state.board)
        prefs[currentPlayerKey] = state.currentPlayer.token
    }

    private fun encodeBoard(board: List<TicTacToePlayer?>): String {
        val normalized = buildList(TicTacToeGameState.CELL_COUNT) {
            repeat(TicTacToeGameState.CELL_COUNT) { index ->
                add(board.getOrNull(index))
            }
        }
        return normalized.joinToString(separator = "") { cell -> cell?.token ?: "-" }
    }

    private fun decodeBoard(raw: String?): List<TicTacToePlayer?> {
        if (raw == null || raw.length != TicTacToeGameState.CELL_COUNT) {
            return List(TicTacToeGameState.CELL_COUNT) { null }
        }
        return raw.map { token ->
            TicTacToePlayer.fromToken(token.toString())
        }
    }
}
