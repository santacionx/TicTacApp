package com.example.tictac

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.GridLayout
import android.widget.TextView
import java.util.Random

class MainActivity : AppCompatActivity() {

    private val gameBoard = Array(3) { arrayOfNulls<String>(3) }
    private var currentPlayer = "" // Starting player, will be set randomly
    private var playerXWins = 0
    private var playerOWins = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initializeGameBoard()
        setButtonClickListeners()

        // Randomly select the starting player (X or O)
        currentPlayer = if (Random().nextBoolean()) "X" else "O"

        // Display the initial game status
        displayGameStatus()

        // New Game Button Click Listener
        val buttonNewGame: Button = findViewById(R.id.buttonNewGame)
        buttonNewGame.setOnClickListener {
            resetGame()
        }
        // Initialize the TextViews for Player X and Player O scores
        val playerXScoreTextView: TextView = findViewById(R.id.playerXScore)
        val playerOScoreTextView: TextView = findViewById(R.id.playerOScore)

        // Set default scores
        val defaultScore = resources.getString(R.string.default_score) // Assuming there's a string resource for default score

        playerXScoreTextView.text = defaultScore
        playerOScoreTextView.text = defaultScore

         fun resetGameBoard() {
            val layout: GridLayout = findViewById(R.id.gridLayout)
            for (i in 0 until layout.childCount) {
                val child: View = layout.getChildAt(i)
                if (child is Button) {
                    child.isEnabled = true
                    child.text = ""
                }
            }
            initializeGameBoard() // Reset the game logic
            currentPlayer = if (Random().nextBoolean()) "X" else "O" // Randomly set the starting player
        }

         fun resetScores() {
            playerXWins = 0
            playerOWins = 0
        }

         fun updateScoreDisplay() {
            val playerXScoreTextView: TextView? = findViewById(R.id.playerXScore)
            val playerOScoreTextView: TextView? = findViewById(R.id.playerOScore)

            val playerXScoreText = resources.getString(R.string.player_x_score, playerXWins)
            val playerOScoreText = resources.getString(R.string.player_o_score, playerOWins)

            playerXScoreTextView?.text = playerXScoreText
            playerOScoreTextView?.text = playerOScoreText
        }
        fun resetGameAndScores() {
            resetGameBoard() // Reset the game board to initial state
            resetScores() // Reset player scores to zero
            updateScoreDisplay() // Update the score display on the UI
            displayGameStatus() // Display the initial game status
        }
        // Function to reset the game and scores to initial state

        val buttonReset: Button = findViewById(R.id.buttonReset)
        buttonReset.setOnClickListener {
            resetGameAndScores() // Reset game board and scores
        }



    }

    // Initialize the game board with empty cells
    private fun initializeGameBoard() {
        for (i in 0 until 3) {
            for (j in 0 until 3) {
                gameBoard[i][j] = " "
            }
        }
    }

    // Set click listeners for the buttons in the GridLayout
    private fun setButtonClickListeners() {
        val buttons = Array(3) { arrayOfNulls<Button>(3) }

        buttons[0][0] = findViewById(R.id.button1)
        buttons[0][1] = findViewById(R.id.button2)
        buttons[0][2] = findViewById(R.id.button3)
        buttons[1][0] = findViewById(R.id.button4)
        buttons[1][1] = findViewById(R.id.button5)
        buttons[1][2] = findViewById(R.id.button6)
        buttons[2][0] = findViewById(R.id.button7)
        buttons[2][1] = findViewById(R.id.button8)
        buttons[2][2] = findViewById(R.id.button9)

        for (i in 0 until 3) {
            for (j in 0 until 3) {
                buttons[i][j]?.setOnClickListener {
                    handleButtonClick(i, j, buttons[i][j]!!)
                }
            }
        }
    }

    // Handle button clicks
    private fun handleButtonClick(row: Int, col: Int, button: Button) {
        if (gameBoard[row][col] == " ") {
            gameBoard[row][col] = currentPlayer
            button.text = currentPlayer

            if (checkWin(currentPlayer)) {
                displayGameResult("$currentPlayer wins!")
                updateScore(currentPlayer)
                disableAllButtons()
            } else if (checkDraw()) {
                displayGameResult("It's a draw!")
                disableAllButtons()
            } else {
                currentPlayer = if (currentPlayer == "X") "O" else "X"
                displayGameStatus()
            }
        }
    }

    private fun checkWin(player: String): Boolean {
        // Check rows, columns, and diagonals for a win
        for (i in 0 until 3) {
            // Check rows
            if (gameBoard[i][0] == player && gameBoard[i][1] == player && gameBoard[i][2] == player) {
                return true
            }
            // Check columns
            if (gameBoard[0][i] == player && gameBoard[1][i] == player && gameBoard[2][i] == player) {
                return true
            }
        }
        // Check diagonals
        if (gameBoard[0][0] == player && gameBoard[1][1] == player && gameBoard[2][2] == player) {
            return true
        }
        if (gameBoard[0][2] == player && gameBoard[1][1] == player && gameBoard[2][0] == player) {
            return true
        }
        return false
    }

    private fun checkDraw(): Boolean {
        for (row in gameBoard) {
            for (cell in row) {
                if (cell == " ") {
                    return false // If any cell is empty, the game is not a draw
                }
            }
        }
        return true // If all cells are filled and there's no winner, it's a draw
    }

    // Update the game status in the UI
    private fun displayGameStatus() {
        val statusTextView: TextView = findViewById(R.id.textViewPlayerTurn)
        val playerTurnString = getString(R.string.player_turn, currentPlayer)
        statusTextView.text = playerTurnString
    }

    // Display the game result in the UI
    private fun displayGameResult(result: String) {
        val statusTextView: TextView = findViewById(R.id.textViewPlayerTurn)
        statusTextView.text = result
    }

    // Disable all buttons after the game ends
    private fun disableAllButtons() {
        val layout: GridLayout = findViewById(R.id.gridLayout)
        for (i in 0 until layout.childCount) {
            val child: View = layout.getChildAt(i)
            if (child is Button) {
                child.isEnabled = false
            }
        }
    }

    // Reset the game board for a new game
    private fun resetGame() {
        val layout: GridLayout = findViewById(R.id.gridLayout)
        for (i in 0 until layout.childCount) {
            val child: View = layout.getChildAt(i)
            if (child is Button) {
                child.isEnabled = true
                child.text = ""
            }
        }
        initializeGameBoard() // Reset the game logic
        currentPlayer = if (Random().nextBoolean()) "X" else "O" // Randomly set the starting player
        displayGameStatus() // Update the game status
    }

// ... (your existing code)

    private fun updateScore(winner: String) {
        if (winner == "X") {
            playerXWins++
        } else {
            playerOWins++
        }
        // Update UI to display scores if needed
        val playerXScoreTextView: TextView? = findViewById(R.id.playerXScore)
        val playerOScoreTextView: TextView? = findViewById(R.id.playerOScore)

        // Update scores using string resources with placeholders
        val playerXScoreText = resources.getString(R.string.player_x_score, playerXWins)
        val playerOScoreText = resources.getString(R.string.player_o_score, playerOWins)

        playerXScoreTextView?.text = playerXScoreText
        playerOScoreTextView?.text = playerOScoreText
    }

}

