package com.app.oving6.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.oving6.server.ChatClient
import com.app.oving6.server.ChatServer
import com.app.oving6.server.Connection
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.IOException

const val PORT = 12345
const val IP_ADDRESS = "10.0.2.2"

class ChatViewModel : ViewModel() {
    private val server = ChatServer(PORT)
    private val client = ChatClient(IP_ADDRESS, PORT)
    private val connections = mutableListOf<Connection>()
    private val state = MutableStateFlow(ChatState())
    val uiState = state.asStateFlow()


    fun startAsServer() = start(asServer = true)
    fun startAsClient() = start(asServer = false)

    private fun start(asServer: Boolean) {
        if (state.value.isServer != null) { return; }
        state.update { it.copy(isServer = asServer) }

        viewModelScope.launch {
            try {
                if (asServer) {
                    while (true) {
                        val socket = server.awaitClient()
                        handle(Connection(socket))
                    }
                }
                else {
                    val socket = client.connect()
                    handle(Connection(socket))
                }
            } catch (e: IOException) {
            }
        }

    }

    private fun handle(connection: Connection) {
        //register new connection and update connection size
        connections.add(connection)
        state.update { it.copy(connected = connections.size) }

        viewModelScope.launch {

            try {
                while (true) {
                    val line = connection.receive() ?: break
                    state.update { it.copy(messages = it.messages + Message(byMe = false, contents = line)) }
                    if (state.value.isServer == true) {
                        sendToOthers(line, connection)
                    }
                }
            } catch (e: IOException) {
            }

            connections.remove(connection)
            connection.close()
            state.update {
                it.copy(
                    connected = connections.size,
                    messages = it.messages + Message(byMe = false, contents = "", goodbye = true)
                )
            }
        }
    }

    fun send(text: String) {
        if (connections.isEmpty()) return
        viewModelScope.launch {
            sendToOthers(text, sender = null)   // from oneself, nobody to skip
            state.update { it.copy(messages = it.messages + Message(byMe = true, contents = text)) }
        }
    }

    private suspend fun sendToOthers(line: String, sender: Connection?) {
        for (connection in connections.toList()) {
            //skip the sender
            if (connection === sender) continue
            try {
                connection.send(line)
            } catch (e: IOException) {
            }
        }
    }

    override fun onCleared() {
        connections.forEach { it.close() }
        server.close()
        client.close()
    }
}
