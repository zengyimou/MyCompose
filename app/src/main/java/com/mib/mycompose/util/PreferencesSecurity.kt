package com.mib.mycompose.util

import android.content.Context
import com.mib.mycompose.util.ContextHolder.context

/**
 *  author : cengyimou
 *  date : 2023/12/1 11:37
 *  description :
 */
object PreferencesSecurity {
	private const val PREFERENCE_ACCOUNT_INFO = "account_info"
	private const val ACCOUNT_INFO = "user_info"

	var accountInfo: String
		get() {
			val preferences = getPreferences()
			return preferences.getString(ACCOUNT_INFO, "") ?: ""
		}
		set(json) {
			val preferences = getPreferences()
			preferences.edit().putString(ACCOUNT_INFO, json).apply()
		}

	private fun getPreferences(): SecuritySharedPreference {
		return SecuritySharedPreference(context, PREFERENCE_ACCOUNT_INFO, Context.MODE_PRIVATE)
	}
}