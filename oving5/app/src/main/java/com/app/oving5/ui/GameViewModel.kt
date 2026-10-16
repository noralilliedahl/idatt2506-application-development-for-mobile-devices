package com.app.oving5.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.oving5.server.HttpWrapper
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class GameViewModel : ViewModel() {

    private val http = HttpWrapper("https://bigdata.idi.ntnu.no/mobil/tallspill.jsp")
    private val state = MutableStateFlow(GameState())
    val uiState: StateFlow<GameState> = state.asStateFlow()

    fun onNameChange(value: String) = state.update { it.copy(name = value) }
    fun onCardNumChange(value: String) = state.update { it.copy(cardNum = value) }
    fun onGuessChange (value: String) = state.update { it.copy(guess = value) }

    fun startGame() {
        viewModelScope.launch {
            val reply = http.get(mapOf(
                    "navn" to state.value.name,
                    "kortnummer" to state.value.cardNum)).trim()

            state.update {
                it.copy(started = true,
                    responses = it.responses + reply)}
        }
    }

    fun guess() {
        viewModelScope.launch {
            val response = http.post(mapOf(
                "tall" to state.value.guess)).trim()

            state.update { it.copy(guess = "", responses = it.responses + response) }
        }
    }

    fun startOver() {
        state.update { it.copy(started = false, guess = "", responses = emptyList()) }
    }
}