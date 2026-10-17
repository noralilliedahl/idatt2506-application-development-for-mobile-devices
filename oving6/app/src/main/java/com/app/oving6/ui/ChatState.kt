package com.app.oving6.ui

data class ChatState(
    val isServer: Boolean? = null,
    val messages: List<Message> = emptyList(),
    val connected: Int = 0
)