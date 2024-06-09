package dev.mdklatt.idea.tcpclient.tcp

import java.net.Socket


/**
 * Communicate with a server via TCP.
 *
 * @param host: target host
 * @param port: host port number
 */
class TcpClient(host: String, port: Int) {

    private val socket = Socket(host, port)

    /**
     * Receive all awaiting bytes from the host.
     *
     * @return bytes received
     */
    fun recv(): ByteArray {
        val stream = socket.getInputStream()
        return stream.readBytes()
    }

    /**
     * Send bytes to the host.
     *
     * @param bytes: bytes sent to the host
     */
    fun send(bytes: ByteArray) {
        val stream = socket.getOutputStream()
        stream.write(bytes)
        stream.flush()
    }

    /**
     * Close the connection to the host.
     */
    fun close() {
        socket.close()
    }
}
