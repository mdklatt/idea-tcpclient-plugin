package dev.mdklatt.idea.tcpclient.tcp

import kotlin.test.Test
import kotlin.test.assertTrue


/**
 * Unit tests for the TcpClient class.
 */
class TcpClientTest {
    /**
     * Test two-way communication with a server.
     */
    @Test
    fun testCommunication() {
        val request =  """
            GET / HTTP/1.1\r
            \r
        """.trimIndent()
        val client = TcpClient("www.example.com", 80)
        client.send(request.encodeToByteArray())
        val response = client.recv().decodeToString()
        client.close()
        assertTrue(response.startsWith("HTTP/1.1 505"))
        return
    }
}
