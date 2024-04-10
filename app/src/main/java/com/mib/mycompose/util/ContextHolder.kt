package com.mib.mycompose.util

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import java.lang.ref.WeakReference

/**
 *  author : cengyimou
 *  date : 2023/11/29 15:46
 *  description :
 */
@SuppressLint("StaticFieldLeak")
object ContextHolder {
	lateinit var context: Context
		private set

	fun init(app: Application) {
		this.context = app.applicationContext
	}
}