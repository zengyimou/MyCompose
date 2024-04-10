package com.mib.mycompose.retrofit

import okhttp3.Interceptor
import okhttp3.OkHttpClient

/**
 *  author : cengyimou
 *  date : 2024/4/9 10:01
 *  description :
 */
class RetrofitConfig {
	var serverUrl: String = ""
		private set
	private val interceptors = mutableListOf<Interceptor>()

	private val networkInterceptors = mutableListOf<Interceptor>()

	var connectTimeout: Long = DEFAULT_TIMEOUT
		private set

	var readTimeout: Long = DEFAULT_TIMEOUT
		private set

	var writeTimeout: Long = DEFAULT_TIMEOUT
		private set

	var successCode: String = CODE_SUCCESS
		private set

	var tokenOutCode: String = CODE_TOKEN_OUT
		private set

	var retryOnConnectionFailure: Boolean = true
		private set

	var okHttpClient : OkHttpClient? = null

	fun setServerUrl(serverUrl: String): RetrofitConfig {
		this.serverUrl = serverUrl
		return this
	}

	fun setOkHttpClient(okHttpClient: OkHttpClient) : RetrofitConfig {
		this.okHttpClient = okHttpClient
		return this
	}

	fun addInterceptor(interceptor: Interceptor): RetrofitConfig {
		if (!interceptors.contains(interceptor)) {
			interceptors.add(interceptor)
		}
		return this
	}

	fun addNetworkInterceptor(interceptor: Interceptor): RetrofitConfig {
		if (!networkInterceptors.contains(interceptor)) {
			networkInterceptors.add(interceptor)
		}
		return this
	}

	fun setConnectTimeout(seconds: Long): RetrofitConfig {
		if (seconds > 0) {
			connectTimeout = seconds
		}
		return this
	}

	fun setReadTimeout(seconds: Long): RetrofitConfig {
		if (seconds > 0) {
			readTimeout = seconds
		}
		return this
	}

	fun setWriteTimeout(seconds: Long): RetrofitConfig {
		if (seconds > 0) {
			writeTimeout = seconds
		}
		return this
	}

	fun setSuccessCode(code: String): RetrofitConfig {
		successCode = code
		return this
	}

	fun setTokenOutCode(code: String): RetrofitConfig {
		tokenOutCode = code
		return this
	}

	fun setRetryOnConnectionFailure(value: Boolean): RetrofitConfig {
		retryOnConnectionFailure = value
		return this
	}

	fun getInterceptors(): List<Interceptor> = interceptors.toList()

	fun getNetworkInterceptors(): List<Interceptor> = networkInterceptors.toList()

	companion object {
		const val DEFAULT_TIMEOUT: Long = 30
		const val CODE_SUCCESS = "0000"
		const val CODE_TOKEN_OUT = "5000"
	}
}