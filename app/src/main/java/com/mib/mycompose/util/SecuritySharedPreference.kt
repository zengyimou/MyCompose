package com.mib.mycompose.util

import android.annotation.TargetApi
import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import android.preference.PreferenceManager
import android.util.Log
import androidx.annotation.RequiresApi
import com.mib.mycompose.endecryption.EncryptUtils
import java.util.HashMap
import java.util.HashSet

/**
 *  author : cengyimou
 *  date : 2023/12/1 11:38
 *  description :
 */
class SecuritySharedPreference(private val context: Context, name: String?, mode: Int) :
	SharedPreferences {
	private var sharedPreferences: SharedPreferences? = null
	override fun getAll(): Map<String, String?> {
		val encryptMap = sharedPreferences!!.all
		val decryptMap: MutableMap<String, String?> = HashMap()
		for ((key, cipherText) in encryptMap) {
			if (cipherText != null) {
				decryptMap[key] = cipherText.toString()
			}
		}
		return decryptMap
	}

	/**
	 * encrypt function
	 *
	 * @return cipherText base64
	 */
	private fun encryptPreference(plainText: String): String {
		return EncryptUtils.getInstance(context).encrypt(plainText) ?: ""
	}

	/**
	 * decrypt function
	 *
	 * @return plainText
	 */
	private fun decryptPreference(cipherText: String): String {
		return EncryptUtils.getInstance(context).decrypt(cipherText) ?: ""
	}

	override fun getString(key: String, defValue: String?): String? {
		val encryptValue = sharedPreferences!!.getString(encryptPreference(key), null)
		return encryptValue?.let { decryptPreference(it) } ?: defValue
	}

	@RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
	override fun getStringSet(key: String, defValues: Set<String>?): Set<String>? {
		val encryptSet = sharedPreferences!!.getStringSet(encryptPreference(key), null)
			?: return defValues
		val decryptSet: MutableSet<String> = HashSet()
		for (encryptValue in encryptSet) {
			decryptSet.add(decryptPreference(encryptValue))
		}
		return decryptSet
	}

	override fun getInt(key: String, defValue: Int): Int {
		val encryptValue = sharedPreferences!!.getString(encryptPreference(key), null)
			?: return defValue
		return decryptPreference(encryptValue).toInt()
	}

	override fun getLong(key: String, defValue: Long): Long {
		val encryptValue = sharedPreferences!!.getString(encryptPreference(key), null)
			?: return defValue
		return decryptPreference(encryptValue).toLong()
	}

	override fun getFloat(key: String, defValue: Float): Float {
		val encryptValue = sharedPreferences!!.getString(encryptPreference(key), null)
			?: return defValue
		return decryptPreference(encryptValue).toFloat()
	}

	override fun getBoolean(key: String, defValue: Boolean): Boolean {
		val encryptValue = sharedPreferences!!.getString(encryptPreference(key), null)
			?: return defValue
		return java.lang.Boolean.parseBoolean(decryptPreference(encryptValue))
	}

	override fun contains(key: String): Boolean {
		return sharedPreferences!!.contains(encryptPreference(key))
	}

	override fun edit(): SecurityEditor {
		return SecurityEditor()
	}

	override fun registerOnSharedPreferenceChangeListener(listener: SharedPreferences.OnSharedPreferenceChangeListener) {
		sharedPreferences!!.registerOnSharedPreferenceChangeListener(listener)
	}

	override fun unregisterOnSharedPreferenceChangeListener(listener: SharedPreferences.OnSharedPreferenceChangeListener) {
		sharedPreferences!!.unregisterOnSharedPreferenceChangeListener(listener)
	}

	/**
	 * 处理加密过渡
	 */
	fun handleTransition() {
		val oldMap = sharedPreferences!!.all
		val newMap: MutableMap<String, String> = HashMap()
		for ((key, value) in oldMap) {
			Log.i(TAG, "key:$key, value:$value")
			newMap[encryptPreference(key)] = encryptPreference(value.toString())
		}
		val editor = sharedPreferences!!.edit()
		editor.clear().apply()
		for ((key, value) in newMap) {
			editor.putString(key, value)
		}
		editor.apply()
	}

	/**
	 * 自动加密Editor
	 */
	inner class SecurityEditor : SharedPreferences.Editor {
		private val editor: SharedPreferences.Editor = sharedPreferences!!.edit()
		override fun putString(key: String, value: String?): SharedPreferences.Editor {
			editor.putString(encryptPreference(key), encryptPreference(value ?: ""))
			return this
		}

		@RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
		override fun putStringSet(key: String, values: Set<String>?): SharedPreferences.Editor {
			val encryptSet: MutableSet<String> = HashSet()
			for (value in values!!) {
				encryptSet.add(encryptPreference(value))
			}
			editor.putStringSet(encryptPreference(key), encryptSet)
			return this
		}

		override fun putInt(key: String, value: Int): SharedPreferences.Editor {
			editor.putString(encryptPreference(key), encryptPreference(value.toString()))
			return this
		}

		override fun putLong(key: String, value: Long): SharedPreferences.Editor {
			editor.putString(
				encryptPreference(key),
				encryptPreference(value.toString())
			)
			return this
		}

		override fun putFloat(key: String, value: Float): SharedPreferences.Editor {
			editor.putString(
				encryptPreference(key),
				encryptPreference(value.toString())
			)
			return this
		}

		override fun putBoolean(key: String, value: Boolean): SharedPreferences.Editor {
			editor.putString(
				encryptPreference(key),
				encryptPreference(java.lang.Boolean.toString(value))
			)
			return this
		}

		override fun remove(key: String): SharedPreferences.Editor {
			editor.remove(encryptPreference(key))
			return this
		}

		/**
		 * Mark in the editor to remove all values from the preferences.
		 *
		 * @return this
		 */
		override fun clear(): SharedPreferences.Editor {
			editor.clear()
			return this
		}

		/**
		 * 提交数据到本地
		 *
		 * @return Boolean 判断是否提交成功
		 */
		override fun commit(): Boolean {
			return editor.commit()
		}

		/**
		 * Unlike commit(), which writes its preferences out to persistent storage synchronously,
		 * apply() commits its changes to the in-memory SharedPreferences immediately but starts
		 * an asynchronous commit to disk and you won't be notified of any failures.
		 */
		@TargetApi(Build.VERSION_CODES.GINGERBREAD)
		override fun apply() {
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
				editor.apply()
			} else {
				commit()
			}
		}

	}

	companion object {
		private const val TAG = "SecuritySPref"
	}

	/**
	 * constructor
	 *
	 * @param context should be ApplicationContext not activity
	 * @param name    file contactName
	 * @param mode    context mode
	 */
	init {
		sharedPreferences = if (name.isNullOrEmpty()) {
			PreferenceManager.getDefaultSharedPreferences(context)
		} else {
			context.getSharedPreferences(name, mode)
		}
	}
}