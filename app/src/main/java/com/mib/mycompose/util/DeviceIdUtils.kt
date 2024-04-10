package com.mib.mycompose.util

/**
 *  author : cengyimou
 *  date : 2023/12/1 11:33
 *  description :
 */
import android.Manifest
import android.content.Context
import android.net.wifi.WifiManager
import android.os.Build
import android.provider.Settings
import android.telephony.TelephonyManager
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.annotation.RequiresPermission
import java.io.BufferedReader
import java.io.File
import java.io.FileReader
import java.net.NetworkInterface
import java.nio.charset.StandardCharsets
import java.security.MessageDigest
import java.util.*
import kotlin.experimental.and

object DeviceIdUtils {
	private const val TAG = "DeviceIdUtils"

	/**
	 * 获取设备IMEI
	 * Android 6.0以下：无需申请权限，可以通过getDeviceId()方法获取到IMEI码
	 * Android 6.0-Android 8.0：需要申请READ_PHONE_STATE权限，可以通过getDeviceId()方法获取到IMEI码，如果用户拒绝了权限，会抛出java.lang.SecurityException异常
	 * Android 8.0-Android 10：需要申请READ_PHONE_STATE权限，可以通过getImei()方法获取到IMEI码，如果用户拒绝了权限，会抛出java.lang.SecurityException异常
	 * Android 10及以上：分为以下两种情况：
	 *  targetSdkVersion<29：没有申请权限的情况，通过getImei()方法获取IMEI码时抛出java.lang.SecurityException异常；申请了权限，通过getImei()方法获取到IMEI码为null
	 *  targetSdkVersion=29：无论是否申请了权限，通过getImei()方法获取IMEI码时都会直接抛出java.lang.SecurityException异常
	 */
	@RequiresPermission(Manifest.permission.READ_PHONE_STATE)
	fun getImei(context: Context): String? {
		try {
			val tm = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
			return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
				tm.imei
			} else {
				tm.deviceId
			}
		} catch (ex: Exception) {
			Log.e(TAG, ex.message, ex)
		}
		return ""
	}

	/**
	 * 获得设备的AndroidId
	 *
	 * root、刷机或恢复出厂设置都会导致设备的ANDROID_ID发生改变。
	 * 此外，某些厂商定制系统的Bug会导致不同的设备可能会产生相同的ANDROID_ID，而且某些设备获取到的ANDROID_ID为null
	 *
	 * @return 设备的AndroidId或空白字符串
	 */
	fun getAndroidId(context: Context): String? {
		try {
			return Settings.System.getString(context.contentResolver, Settings.Secure.ANDROID_ID)
		} catch (ex: Exception) {
			Log.e(TAG, ex.message, ex)
		}
		return null
	}

	/**
	 * 获得设备序列号（如：WTK7N16923005607）, 个别设备无法获取
	 *
	 * Android 8.0以下：无需申请权限，可以通过Build.SERIAL获取到设备序列号
	 * Android 8.0-Android 10：需要申请READ_PHONE_STATE权限，可以通过Build.getSerial()获取到设备序列号，如果用户拒绝了权限，会抛出java.lang.SecurityException异常
	 * Android 10及以上：分为以下两种情况：
	 *  targetSdkVersion<29：没有申请权限的情况，调用Build.getSerial()方法时抛出java.lang.SecurityException异常；申请了权限，通过Build.getSerial()方法获取到的设备序列号为“unknown”
	 *  targetSdkVersion=29：无论是否申请了权限，调用Build.getSerial()方法时都会直接抛出java.lang.SecurityException异常
	 *
	 * @return 设备序列号
	 */
	@RequiresPermission(Manifest.permission.READ_PHONE_STATE)
	fun getSerial(): String? {
		try {
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
				return Build.getSerial()
			}
			return Build.SERIAL
		} catch (ex: Exception) {
			Log.e(TAG, ex.message, ex)
		}
		return null
	}

	/**
	 * 获取MAC地址
	 */
	fun getMacAddress(context: Context): String? {
		if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
			//Android 6.0以下版本可以获取到设备的mac地址
			//需要声明ACCESS_WIFI_STATE权限，并且设备需要开启wifi
			val wifiManager =
				context.applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
			return wifiManager.connectionInfo.macAddress
		} else if (Build.VERSION.SDK_INT == Build.VERSION_CODES.M) {
			//从Android 6.0开始，使用上述方法获取到的mac地址都为02:00:00:00:00:00
			try {
				return BufferedReader(FileReader(File("/sys/class/net/wlan0/address"))).readLine()
			} catch (ex: Exception) {
				Log.e(TAG, ex.message, ex)
			}
		} else {
			//Android 7.0开始执行上面的代码会抛出java.io.FileNotFoundException: /sys/class/net/wlan0/address
			//(Permission denied)异常，没有权限读取该文件
			try {
				val all: List<NetworkInterface> =
					Collections.list(NetworkInterface.getNetworkInterfaces())
				for (nif in all) {
					if (!nif.name.equals("wlan0", true)) {
						continue
					}
					val macBytes: ByteArray = nif.hardwareAddress ?: return null
					val res1 = StringBuilder()
					for (b in macBytes) {
						res1.append(String.format("%02X:", b))
					}
					if (res1.isNotEmpty()) {
						res1.deleteCharAt(res1.length - 1)
					}
					return res1.toString()
				}
			} catch (e: Exception) {
				Log.e(TAG, e.message, e)
			}
			return null
		}
		return null
	}

	/**
	 * 获得设备硬件uuid
	 * 使用硬件信息，计算出一个随机数
	 *
	 * @return 设备硬件uuid
	 */
	private val deviceUUID: String
		@RequiresApi(Build.VERSION_CODES.O)
		@RequiresPermission(Manifest.permission.READ_PHONE_STATE)
		get() {
			try {
				val dev = "3883756" +
						Build.BOARD.length % 10 +
						Build.BRAND.length % 10 +
						Build.DEVICE.length % 10 +
						Build.HARDWARE.length % 10 +
						Build.ID.length % 10 +
						Build.MODEL.length % 10 +
						Build.PRODUCT.length % 10 +
						Build.SERIAL.length % 10
				return UUID(
					dev.hashCode().toLong(),
					getSerial().hashCode().toLong()
				).toString()
			} catch (ex: Exception) {
				Log.e(TAG, ex.message, ex)
				return ""
			}
		}

	/**
	 * 获得设备硬件标识
	 *
	 * @param context 上下文
	 * @return 设备硬件标识
	 */
	@RequiresPermission(Manifest.permission.READ_PHONE_STATE)
	fun getDeviceId(
		context: Context,
		readDeviceIdCallback: ReadDeviceIdCallback = DeviceIdUtils::getSavedDeviceId,
		saveDeviceIdCallback: SaveDeviceIdCallback = DeviceIdUtils::saveDeviceId
	): String {
		val savedDeviceId = readDeviceIdCallback(context)
		if (!savedDeviceId.isNullOrEmpty()) {
			return savedDeviceId
		}
		val sbDeviceId = StringBuilder()

		//获得设备默认IMEI（>=6.0 需要ReadPhoneState权限）
		val imei = getImei(context)
		//获得AndroidId（无需权限）
		val androidId = getAndroidId(context)
		//获得设备序列号（无需权限）
		val serial = getSerial()
		//获得硬件uuid（根据硬件相关属性，生成uuid）（无需权限）
		val uuid = deviceUUID.replace("-", "")

		//追加IMEI
		if (!imei.isNullOrEmpty()) {
			sbDeviceId.append(imei)
			sbDeviceId.append("|")
		}
		//追加androidId
		if (!androidId.isNullOrEmpty()) {
			sbDeviceId.append(androidId)
			sbDeviceId.append("|")
		}
		//追加serial
		if (!serial.isNullOrEmpty()) {
			sbDeviceId.append(serial)
			sbDeviceId.append("|")
		}
		//追加硬件uuid
		if (uuid.isNotEmpty()) {
			sbDeviceId.append(uuid)
		}

		//生成SHA1，统一DeviceId长度
		if (sbDeviceId.isNotEmpty()) {
			try {
				val hash = getHashByString(sbDeviceId.toString())
				val sha1 = bytesToHex(hash)
				if (sha1.isNotEmpty()) {
					//返回最终的DeviceId
					saveDeviceIdCallback(context, sha1)
					return sha1
				}
			} catch (ex: Exception) {
				Log.e(TAG, ex.message, ex)
			}
		}

		//如果以上硬件标识数据均无法获得，
		//则DeviceId默认使用系统随机数，这样保证DeviceId不为空
		val deviceId = UUID.randomUUID().toString().replace("-", "")
		saveDeviceIdCallback(context, deviceId)
		return deviceId
	}


	/**
	 * 获得设备硬件标识(不再包含androidId因子)
	 *
	 * @param context 上下文
	 * @return 设备硬件标识
	 */
	@RequiresPermission(Manifest.permission.READ_PHONE_STATE)
	fun getDeviceId2(
		context: Context,
		readDeviceIdCallback: ReadDeviceIdCallback = DeviceIdUtils::getSavedDeviceId,
		saveDeviceIdCallback: SaveDeviceIdCallback = DeviceIdUtils::saveDeviceId
	): String {
		val savedDeviceId = readDeviceIdCallback(context)
		if (!savedDeviceId.isNullOrEmpty()) {
			return savedDeviceId
		}
		val sbDeviceId = StringBuilder()

		//获得设备默认IMEI（>=6.0 需要ReadPhoneState权限）
		val imei = getImei(context)

		//获得设备序列号（无需权限）
		val serial = getSerial()
		//获得硬件uuid（根据硬件相关属性，生成uuid）（无需权限）
		val uuid = deviceUUID.replace("-", "")

		//追加IMEI
		if (!imei.isNullOrEmpty()) {
			sbDeviceId.append(imei)
			sbDeviceId.append("|")
		}
		//追加serial
		if (!serial.isNullOrEmpty()) {
			sbDeviceId.append(serial)
			sbDeviceId.append("|")
		}
		//追加硬件uuid
		if (uuid.isNotEmpty()) {
			sbDeviceId.append(uuid)
		}

		//生成SHA1，统一DeviceId长度
		if (sbDeviceId.isNotEmpty()) {
			try {
				val hash = getHashByString(sbDeviceId.toString())
				val sha1 = bytesToHex(hash)
				if (sha1.isNotEmpty()) {
					//返回最终的DeviceId
					saveDeviceIdCallback(context, sha1)
					return sha1
				}
			} catch (ex: Exception) {
				Log.e(TAG, ex.message, ex)
			}
		}

		//如果以上硬件标识数据均无法获得，
		//则DeviceId默认使用系统随机数，这样保证DeviceId不为空
		val deviceId = UUID.randomUUID().toString().replace("-", "")
		saveDeviceIdCallback(context, deviceId)
		return deviceId
	}

	/**
	 * 取SHA1
	 *
	 * @param data 数据
	 * @return 对应的hash值
	 */
	private fun getHashByString(data: String): ByteArray {
		return try {
			val messageDigest = MessageDigest.getInstance("SHA1")
			messageDigest.reset()
			messageDigest.update(data.toByteArray(StandardCharsets.UTF_8))
			messageDigest.digest()
		} catch (e: Exception) {
			"".toByteArray()
		}
	}

	/**
	 * 转16进制字符串
	 *
	 * @param data 数据
	 * @return 16进制字符串
	 */
	private fun bytesToHex(data: ByteArray): String {
		val sb = StringBuilder()
		var stmp: String
		for (n in data.indices) {
			stmp = Integer.toHexString((data[n] and 0xFF.toByte()).toInt())
			if (stmp.length == 1) {
				sb.append("0")
			}
			sb.append(stmp)
		}
		return sb.toString().toUpperCase(Locale.CHINA)
	}

	/**
	 * 在Android Q上获取设备唯一ID
	 */
	@RequiresPermission(Manifest.permission.READ_PHONE_STATE)
	fun getUUID(): String {
		val devIdShort = "35" +
				Build.BOARD.length % 10 + Build.BRAND.length % 10 +
				Build.CPU_ABI.length % 10 + Build.DEVICE.length % 10 +
				Build.DISPLAY.length % 10 + Build.HOST.length % 10 +
				Build.ID.length % 10 + Build.MANUFACTURER.length % 10 +
				Build.MODEL.length % 10 + Build.PRODUCT.length % 10 +
				Build.TAGS.length % 10 + Build.TYPE.length % 10 +
				Build.USER.length % 10 //13 位
		val serial = try {
			val s = getSerial() ?: ""
			return UUID(devIdShort.hashCode().toLong(), s.hashCode().toLong()).toString()
		} catch (e: Exception) {
			//serial需要一个初始化
			"serial"
		}

		//使用硬件信息拼凑出来的15位号码
		return UUID(devIdShort.hashCode().toLong(), serial.hashCode().toLong()).toString()
	}

	private fun getSavedDeviceId(context: Context): String? {
		return SPUtils.getSharedStringData(context, KEY_DEVICE_ID)
	}


	private fun saveDeviceId(context: Context, deviceId: String) {
		SPUtils.setSharedStringData(context, KEY_DEVICE_ID, deviceId)
	}

	private const val KEY_DEVICE_ID = "device_id"
}

/**
 * 保存DeviceId回调
 */
typealias SaveDeviceIdCallback = (context: Context, deviceId: String) -> Unit

/**
 * 读取DeviceId回调
 */
typealias ReadDeviceIdCallback = (context: Context) -> String?