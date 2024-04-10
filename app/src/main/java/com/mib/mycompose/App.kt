package com.mib.mycompose

import com.mib.mycompose.base.BaseApplication
import com.mib.mycompose.manager.UserInfoManager
import com.mib.mycompose.net.CustomLogInterceptor
import com.mib.mycompose.net.RequestInterceptor
import com.mib.mycompose.retrofit.RetrofitConfig
import com.mib.mycompose.retrofit.RetrofitFactory
import com.mib.mycompose.util.ContextHolder
import com.mib.mycompose.util.Logger
import okhttp3.Interceptor

/**
 *  author : cengyimou
 *  date : 2023/11/30 19:11
 *  description :
 */
class App: BaseApplication() {

	override fun onCreate() {
		super.onCreate()
		ContextHolder.init(this)
		UserInfoManager.init()

		val config = RetrofitConfig()
			.setConnectTimeout(60L)
			.setWriteTimeout(60L)
			.setReadTimeout(60L)
			.setServerUrl("http://testhk5.xcreditech.com/api-middle/api/")
			.setRetryOnConnectionFailure(true)
			.addInterceptor(RequestInterceptor())
		if (BuildConfig.DEBUG) {
			//日志拦截器
			val httpLoggingInterceptor = getHttpLoggingInterceptor()
			if (httpLoggingInterceptor != null) {
				config.addInterceptor(httpLoggingInterceptor)
			}
			config.addInterceptor(CustomLogInterceptor())
		}
		RetrofitFactory.init(config)
	}

	/**
	 * 反射获取日志拦截器
	 * @return Interceptor?
	 */
	private fun getHttpLoggingInterceptor(): Interceptor? {
		try {
			val clazz = Class.forName("okhttp3.logging.HttpLoggingInterceptor")
			val httpLoggingInterceptor = clazz.newInstance() as? Interceptor
			if (httpLoggingInterceptor != null) {
				val levelEnumClazz = Class.forName("okhttp3.logging.HttpLoggingInterceptor\$Level")
				val enumConstants = levelEnumClazz.enumConstants
				if (!enumConstants.isNullOrEmpty()) {
					for (enumConstant in enumConstants) {
						val enumConstantClazz = enumConstant.javaClass
						val nameMethod = enumConstantClazz.getMethod("name")
						if ("BODY" == nameMethod.invoke(enumConstant)) {
							val setLevelMethod = clazz.getDeclaredMethod("setLevel", levelEnumClazz)
							setLevelMethod.invoke(httpLoggingInterceptor, enumConstant)
							break
						}
					}
				}
			}
			return httpLoggingInterceptor
		} catch (e: Exception) {
			Logger.e(TAG, e.message, e)
		}
		return null
	}

	companion object{
		const val TAG = "App"
	}

}