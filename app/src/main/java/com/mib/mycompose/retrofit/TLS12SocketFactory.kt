package com.mib.mycompose.retrofit

import java.io.IOException
import java.net.InetAddress
import java.net.Socket
import java.net.UnknownHostException
import javax.net.ssl.SSLSocket
import javax.net.ssl.SSLSocketFactory

/**
 *  author : cengyimou
 *  date : 2024/4/9 10:02
 *  description :
 */
internal class TLS12SocketFactory(val base: SSLSocketFactory) : SSLSocketFactory() {
	override fun getDefaultCipherSuites(): Array<String> {
		return base.defaultCipherSuites
	}

	override fun getSupportedCipherSuites(): Array<String> {
		return base.supportedCipherSuites
	}

	@Throws(IOException::class)
	override fun createSocket(s: Socket, host: String, port: Int, autoClose: Boolean): Socket {
		return patch(base.createSocket(s, host, port, autoClose))
	}

	@Throws(IOException::class, UnknownHostException::class)
	override fun createSocket(host: String, port: Int): Socket {
		return patch(base.createSocket(host, port))
	}

	@Throws(IOException::class, UnknownHostException::class)
	override fun createSocket(host: String, port: Int, localHost: InetAddress, localPort: Int): Socket {
		return patch(base.createSocket(host, port, localHost, localPort))
	}

	@Throws(IOException::class)
	override fun createSocket(host: InetAddress, port: Int): Socket {
		return patch(base.createSocket(host, port))
	}

	@Throws(IOException::class)
	override fun createSocket(address: InetAddress, port: Int, localAddress: InetAddress, localPort: Int): Socket {
		return patch(base.createSocket(address, port, localAddress, localPort))
	}

	private fun patch(s: Socket): Socket {
		if (s is SSLSocket) {
			s.enabledProtocols = TLS_V12_ONLY
		}
		return s
	}

	companion object {
		private val TLS_V12_ONLY = arrayOf("TLSv1.2")
	}

}