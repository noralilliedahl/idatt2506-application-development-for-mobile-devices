package com.app.oving5

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.app.oving5.ui.GameScreen
import com.app.oving5.ui.GameViewModel
import com.app.oving5.ui.theme.Oving5Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Oving5Theme {
                val viewModel: GameViewModel = viewModel()
                val state by viewModel.uiState.collectAsStateWithLifecycle()

                GameScreen(
                    state = state,
                    onNameChange = viewModel::onNameChange,
                    onCardNumChange = viewModel::onCardNumChange,
                    onGuessChange = viewModel::onGuessChange,
                    onStartGame = viewModel::startGame,
                    onGuess = viewModel::guess,
                    onStartOver = viewModel::startOver
                )
            }
        }
    }
}
