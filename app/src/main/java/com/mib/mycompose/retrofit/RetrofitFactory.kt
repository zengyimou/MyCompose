package com.mib.mycompose.retrofit

import android.os.Build
import android.util.Log
import okhttp3.ConnectionPool
import okhttp3.ConnectionSpec
import okhttp3.OkHttpClient
import okhttp3.TlsVersion
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.security.KeyStore
import java.util.Arrays
import java.util.concurrent.TimeUnit
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManagerFactory
import javax.net.ssl.X509TrustManager


/**
 *  author : cengyimou
 *  date : 2024/4/9 10:00
 *  description :
 */
object RetrofitFactory {
	private const val TAG = "RetrofitFactory"
	private var okHttpClient: OkHttpClient? = null
	private var retrofit: Retrofit? = null

	/**
	 * 初始化
	 */
	fun init(config: RetrofitConfig) {
		if (config.serverUrl.isBlank()) {
			throw IllegalStateException("You must call init first")
		}
		if (config.okHttpClient != null) {
			okHttpClient = config.okHttpClient
		} else {
			val okHttpBuilder = OkHttpClient.Builder()
			for (interceptor in config.getInterceptors()) {
				okHttpBuilder.addInterceptor(interceptor)
			}
			for (networkInterceptor in config.getNetworkInterceptors()) {
				okHttpBuilder.addNetworkInterceptor(networkInterceptor)
			}
			okHttpBuilder.connectTimeout(config.connectTimeout, TimeUnit.SECONDS)
				.readTimeout(config.readTimeout, TimeUnit.SECONDS)
				.writeTimeout(config.writeTimeout, TimeUnit.SECONDS)
				.retryOnConnectionFailure(config.retryOnConnectionFailure)

			if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP_MR1) {
				val specsList: List<ConnectionSpec>? = getSpecsBelowLollipopMR1(okHttpBuilder)
				if (specsList != null) {
					okHttpBuilder.connectionSpecs(specsList)
				}
			}

			okHttpClient = okHttpBuilder.build()
		}

		retrofit = Retrofit.Builder()
			.baseUrl(config.serverUrl)
			.client(okHttpClient!!)
			.addConverterFactory(GsonConverterFactory.create())
//			.addCallAdapterFactory(RxJava2CallAdapterFactory.create())
			.build()
	}

	private fun getSpecsBelowLollipopMR1(builder: OkHttpClient.Builder): List<ConnectionSpec>? {
		return try {
			val trustManagerFactory = TrustManagerFactory.getInstance(
				TrustManagerFactory.getDefaultAlgorithm()
			)
			trustManagerFactory.init(null as KeyStore?)
			val trustManagers = trustManagerFactory.trustManagers
			check(!(trustManagers.size != 1 || trustManagers[0] !is X509TrustManager)) {
				("Unexpected default trust managers:"
						+ Arrays.toString(trustManagers))
			}
			val sc: SSLContext = SSLContext.getInstance("TLSv1.2")
			sc.init(null, null, null)
			val trustManager = trustManagers.get(0) as X509TrustManager
			builder.sslSocketFactory(sc.socketFactory, trustManager).connectionPool(ConnectionPool(300, 3, TimeUnit.MINUTES)).build()
			val cs = ConnectionSpec.Builder(ConnectionSpec.MODERN_TLS)
				.tlsVersions(TlsVersion.TLS_1_2)
				.build()
			val specs: MutableList<ConnectionSpec> = ArrayList()
			specs.add(cs)
			specs.add(ConnectionSpec.COMPATIBLE_TLS)
			specs
		} catch (exc: Exception) {
			Log.e(TAG, "OkHttpTLSCompat Error while setting TLS 1.2$exc")
			null
		}
	}

	fun <T> createApi(apiServiceClazz: Class<T>): T {
		require(retrofit != null) { "You must call init first" }
		return retrofit!!.create(apiServiceClazz)
	}

	fun getOkHttpClient(): OkHttpClient {
		if (okHttpClient == null) {
			throw IllegalStateException("You must call init first")
		}
		return okHttpClient!!
	}

	fun getOkHttpClientBuilder(): OkHttpClient.Builder {
		if (okHttpClient == null) {
			throw IllegalStateException("You must call init first")
		}
		return okHttpClient!!.newBuilder()
	}

	fun getRetrofitBuilder(): Retrofit.Builder {
		if (retrofit == null) {
			throw IllegalStateException("You must call init first")
		}
		return retrofit!!.newBuilder()
	}

	fun rebuildOkHttpClient(builder: OkHttpClient.Builder) {
		okHttpClient = builder.build()
	}

	fun rebuildRetrofit(builder: Retrofit.Builder) {
		retrofit = builder.build()
	}
}