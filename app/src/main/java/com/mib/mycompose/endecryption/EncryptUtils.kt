package com.mib.mycompose.endecryption

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.provider.Settings
import android.text.TextUtils
import android.util.Base64
import android.util.Log
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec

/**
 *  author : cengyimou
 *  date : 2023/12/1 11:40
 *  description :
 */
class EncryptUtils private constructor(context: Context) {
	private val key: String

	init {
		val serialNo = getDeviceSerialNumber(context)
		//加密随机字符串生成AES key
		key = sha("$serialNo#\$ERZDTS\$D%F^GoxafZX")!!.substring(0, 16)
		Log.e(TAG, key)
	}

	/**
	 * Gets the hardware serial number of this device.
	 *
	 * @return serial number or Settings.Secure.ANDROID_ID if not available.
	 */
	@SuppressLint("HardwareIds")
	private fun getDeviceSerialNumber(context: Context): String {
		// We're using the Reflection API because Build.SERIAL is only available
		// since API Level 9 (Gingerbread, Android 2.3).
		return try {
			val deviceSerial =
				Build::class.java.getField("SERIAL")[null] as String
			if (TextUtils.isEmpty(deviceSerial)) {
				Settings.Secure.getString(
					context.contentResolver,
					Settings.Secure.ANDROID_ID
				)
			} else {
				deviceSerial
			}
		} catch (ignored: Exception) {
			// Fall back  to Android_ID
			Settings.Secure.getString(
				context.contentResolver,
				Settings.Secure.ANDROID_ID
			)
		}
	}

	/**
	 * SHA加密
	 *
	 * @param strText 明文
	 * @return
	 */
	private fun sha(strText: String?): String? {
		// 返回值
		var strResult: String? = null
		// 是否是有效字符串
		if (strText != null && strText.isNotEmpty()) {
			try {
				// SHA 加密开始
				val messageDigest =
					MessageDigest.getInstance("SHA-256")
				// 传入要加密的字符串
				messageDigest.update(strText.toByteArray())
				val byteBuffer = messageDigest.digest()
				val strHexString = StringBuilder()
				for (b in byteBuffer) {
					val hex = Integer.toHexString(0xff and b.toInt())
					if (hex.length == 1) {
						strHexString.append('0')
					}
					strHexString.append(hex)
				}
				strResult = strHexString.toString()
			} catch (e: NoSuchAlgorithmException) {
				Log.e(TAG, e.message, e)
			}
		}
		return strResult
	}

	/**
	 * AES128加密
	 *
	 * @param plainText 明文
	 * @return
	 */
	fun encrypt(plainText: String): String? {
		return try {
			val cipher =
				Cipher.getInstance("AES/ECB/PKCS5Padding")
			val keySpec =
				SecretKeySpec(key.toByteArray(), "AES")
			cipher.init(Cipher.ENCRYPT_MODE, keySpec)
			val encrypted = cipher.doFinal(plainText.toByteArray())
			Base64.encodeToString(encrypted, Base64.NO_WRAP)
		} catch (e: Exception) {
			Log.e(TAG, e.message, e)
			null
		}
	}

	/**
	 * AES128解密
	 *
	 * @param cipherText 密文
	 * @return
	 */
	fun decrypt(cipherText: String?): String? {
		return try {
			val encrypted =
				Base64.decode(cipherText, Base64.NO_WRAP)
			val cipher =
				Cipher.getInstance("AES/ECB/PKCS5Padding")
			val keySpec =
				SecretKeySpec(key.toByteArray(), "AES")
			cipher.init(Cipher.DECRYPT_MODE, keySpec)
			val original = cipher.doFinal(encrypted)
			String(original)
		} catch (e: Exception) {
			Log.e(TAG, e.message, e)
			null
		}
	}

	companion object {
		private var instance: EncryptUtils? = null
		private val TAG = EncryptUtils::class.java.simpleName

		/**
		 * 单例模式
		 *
		 * @param context context
		 * @return
		 */
		fun getInstance(context: Context): EncryptUtils {
			if (instance == null) {
				synchronized(EncryptUtils::class.java) {
					if (instance == null) {
						instance = EncryptUtils(context.applicationContext)
					}
				}
			}
			return instance!!
		}
	}
}