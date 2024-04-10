package com.mib.mycompose.base

import android.app.Application
import android.content.res.Resources

/**
 *  author : cengyimou
 *  date : 2024/4/9 10:27
 *  description :
 */
open class BaseApplication: Application() {

	override fun onCreate() {
		super.onCreate()
		appContext = this
		appResources = appContext.resources
	}

	companion object {
		lateinit var appContext: BaseApplication
			private set

		lateinit var appResources: Resources
			private set
	}
}