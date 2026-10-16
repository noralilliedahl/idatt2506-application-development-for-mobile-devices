package com.app.oving5.server

import java.net.CookieHandler
import java.net.CookieManager
import java.net.URL
import java.net.URLEncoder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

// class from lecture (unchanged)
class HttpWrapper(private val url: String) {
    init {
        CookieHandler.setDefault(CookieManager())
    }
    suspend fun get(parameters: Map<String, String>): String =
        withContext(Dispatchers.IO) {
            val query = encodeParameters(parameters)
            val connection = URL("$url?$query").openConnection()
            connection.inputStream.reader().use { it.readText() }
        }
    suspend fun post(parameters: Map<String, String>): String =
        withContext(Dispatchers.IO) {
            val connection = URL(url).openConnection()
            val type = "application/x-www-form-urlencoded; charset=UTF-8"
            connection.setRequestProperty("Content-Type", type)
            connection.doOutput = true
            val body = encodeParameters(parameters).toByteArray()
            connection.outputStream.use { it.write(body) }
            connection.inputStream.reader().use { it.readText() }
        }
    suspend fun getWithHeaders(parameters: Map<String, String>): String =
        withContext(Dispatchers.IO) {
            val query = encodeParameters(parameters)
            val connection = URL("$url?$query").openConnection()
            val headers = connection.headerFields.entries
            headers.joinToString("\n") + "\n\n" +
                connection.inputStream.reader().use { it.readText() }
        }

    //added method for encoding the parameters
    private fun encodeParameters(parameters: Map<String, String>): String =
        parameters.entries.joinToString("&") { (key, value) ->
            "${URLEncoder.encode(key, "UTF-8")}=${URLEncoder.encode(value, "UTF-8")}"
        }
}
