package com.app.oving5.ui

data class GameState(
    val name: String = "",
    val cardNum: String = "",
    val guess: String = "",
    val started: Boolean = false,
    val responses: List<String> = emptyList()
)


