package com.mib.mycompose.util

/**
 *  author : cengyimou
 *  date : 2023/12/1 11:34
 *  description :
 */
import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager


/**
 * @author lindan SP工具
 * 对SharedPreference文件中的各种类型的数据进行存取操作
 */
object SPUtils {

	private var sp: SharedPreferences? = null

	private fun init(context: Context) {
		if (sp == null) {
			sp = PreferenceManager.getDefaultSharedPreferences(context)
		}
	}

	fun setSharedIntData(context: Context, key: String, value: Int) {
		if (sp == null) {
			init(context)
		}
		sp!!.edit().putInt(key, value).apply()
	}

	fun getSharedIntData(context: Context, key: String, defaultValue: Int = 0): Int {
		if (sp == null) {
			init(context)
		}
		return sp!!.getInt(key, defaultValue)
	}

	fun setSharedLongData(context: Context, key: String, value: Long) {
		if (sp == null) {
			init(context)
		}
		sp!!.edit().putLong(key, value).apply()
	}

	fun getSharedLongData(context: Context, key: String, defaultValue: Long = 0L): Long {
		if (sp == null) {
			init(context)
		}
		return sp!!.getLong(key, defaultValue)
	}

	fun setSharedFloatData(context: Context, key: String, value: Float) {
		if (sp == null) {
			init(context)
		}
		sp!!.edit().putFloat(key, value).apply()
	}

	fun getSharedFloatData(context: Context, key: String, defaultValue: Float = 0F): Float {
		if (sp == null) {
			init(context)
		}
		return sp!!.getFloat(key, defaultValue)
	}

	fun setSharedBooleanData(context: Context, key: String, value: Boolean) {
		if (sp == null) {
			init(context)
		}
		sp!!.edit().putBoolean(key, value).apply()
	}

	fun getSharedBooleanData(
		context: Context,
		key: String,
		defaultValue: Boolean = false
	): Boolean {
		if (sp == null) {
			init(context)
		}
		return sp!!.getBoolean(key, defaultValue)
	}

	fun setSharedStringData(context: Context, key: String, value: String) {
		if (sp == null) {
			init(context)
		}
		sp!!.edit().putString(key, value).apply()
	}

	fun getSharedStringData(context: Context, key: String, defaultValue: String = ""): String {
		if (sp == null) {
			init(context)
		}
		return sp!!.getString(key, defaultValue) ?: defaultValue
	}

	fun getSharedStringDataAllowNull(
		context: Context,
		key: String,
		defaultValue: String? = null
	): String? {
		if (sp == null) {
			init(context)
		}
		return sp!!.getString(key, defaultValue)
	}
}