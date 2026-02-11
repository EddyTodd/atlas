package com.ynmidk.atlas.game

enum class TicTacToePlayer(
    val token: String
) {
    X("X"),
    O("O");

    val next: TicTacToePlayer
        get() = if (this == X) O else X

    companion object {
        fun fromToken(token: String?): TicTacToePlayer? = entries.firstOrNull { it.token == token }
    }
}

data class TicTacToeGameState(
    val board: List<TicTacToePlayer?> = List(CELL_COUNT) { null },
    val currentPlayer: TicTacToePlayer = TicTacToePlayer.X,
    val winner: TicTacToePlayer? = null,
    val isDraw: Boolean = false
) {
    val moveCount: Int
        get() = board.count { it != null }

    val hasMoves: Boolean
        get() = moveCount > 0

    val isFinished: Boolean
        get() = winner != null || isDraw

    val isInProgress: Boolean
        get() = hasMoves && !isFinished

    fun withMove(index: Int): TicTacToeGameState {
        if (index !in board.indices || board[index] != null || isFinished) {
            return this
        }

        val updatedBoard = board.toMutableList().also { it[index] = currentPlayer }
        val gameWinner = evaluateWinner(updatedBoard)
        val gameDraw = gameWinner == null && updatedBoard.none { it == null }
        val nextPlayer = if (gameWinner == null && !gameDraw) currentPlayer.next else currentPlayer

        return copy(
            board = updatedBoard,
            currentPlayer = nextPlayer,
            winner = gameWinner,
            isDraw = gameDraw
        )
    }

    companion object {
        const val CELL_COUNT = 9

        fun fromPersisted(
            board: List<TicTacToePlayer?>,
            currentPlayer: TicTacToePlayer
        ): TicTacToeGameState {
            val winner = evaluateWinner(board)
            val isDraw = winner == null && board.none { it == null }
            return TicTacToeGameState(
                board = board,
                currentPlayer = currentPlayer,
                winner = winner,
                isDraw = isDraw
            )
        }

        fun fresh(): TicTacToeGameState = TicTacToeGameState()

        private fun evaluateWinner(board: List<TicTacToePlayer?>): TicTacToePlayer? {
            val winningLines = listOf(
                listOf(0, 1, 2),
                listOf(3, 4, 5),
                listOf(6, 7, 8),
                listOf(0, 3, 6),
                listOf(1, 4, 7),
                listOf(2, 5, 8),
                listOf(0, 4, 8),
                listOf(2, 4, 6)
            )

            return winningLines.firstNotNullOfOrNull { line ->
                val first = board[line[0]]
                if (first != null && line.all { board[it] == first }) first else null
            }
        }
    }
}
