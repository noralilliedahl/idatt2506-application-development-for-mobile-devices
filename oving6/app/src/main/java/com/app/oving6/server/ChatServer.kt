package com.app.oving6.server

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.ServerSocket
import java.net.Socket

class ChatServer(private val port: Int) {
    // Socketen er et felt, ikke en lokal variabel. Den som rydder
    // opp, må nå den utenfra, og porten skal gjenbrukes.
    private var serverSocket: ServerSocket? = null
    /**
     * Venter til en klient kobler seg til, og gir forbindelsen tilbake.
     * Porten åpnes første gang, og gjenbrukes ved senere kall.
     */
    suspend fun awaitClient(): Socket = withContext(Dispatchers.IO) {
        val listening = serverSocket ?: ServerSocket(port)
        serverSocket = listening
        listening.accept()
    }
    fun close() {
        serverSocket?.close()
    }
}
