package com.app.oving6.server

import java.net.Socket
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ChatClient(private val host: String, private val port: Int) {
    private var socket: Socket? = null
    /**
     * Kobler seg til tjeneren, og gir forbindelsen tilbake.
     */
    suspend fun connect(): Socket = withContext(Dispatchers.IO) {
        val opened = Socket(host, port)
        socket = opened
        opened
    }
    fun close() {
        socket?.close()
    }
}