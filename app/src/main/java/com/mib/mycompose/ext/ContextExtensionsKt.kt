package com.mib.mycompose.ext

import android.content.Context
import android.graphics.drawable.Drawable
import android.net.ConnectivityManager
import android.util.TypedValue
import android.view.Gravity
import android.widget.Toast
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat

/**
 *  author : cengyimou
 *  date : 2024/4/9 14:08
 *  description :
 */
@ColorInt
fun Context.color(@ColorRes colorResId: Int): Int = ContextCompat.getColor(this, colorResId)

/**
 * 在Context上下文环境下获取图片
 */
fun Context.drawable(drawableResId: Int): Drawable =
	ContextCompat.getDrawable(this, drawableResId)!!

//------------------------------Context扩展函数相关 begin-----------------------------------//
/**
 * 显示Toast
 */
fun Context.toast(
	message: String,
	duration: Int = Toast.LENGTH_SHORT,
	gravity: Int = Gravity.BOTTOM
) {
	Toast.makeText(this.applicationContext, message, duration).apply {
		setGravity(gravity, 0, dp2px(48F).toInt())
	}.show()
}

/**
 * 显示Toast
 */
fun Context.toast(@StringRes message: Int, duration: Int = Toast.LENGTH_SHORT) {
	toast(getString(message), duration)
}

/**
 * dp转px
 */
fun Context.dp2px(dpValue: Float) =
	TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpValue, resources.displayMetrics)

/**
 * sp转px
 */
fun Context.sp2px(spValue: Float) =
	TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, spValue, resources.displayMetrics)

/**
 * px转dp
 */
fun Context.px2dp(pxValue: Float): Int {
	val scale = resources.displayMetrics.density
	return (pxValue / scale + 0.5f).toInt()
}

/**
 * px转sp
 */
fun Context.px2Sp(pxValue: Float): Int {
	val scale = resources.displayMetrics.scaledDensity
	return (pxValue / scale + 0.5f).toInt()
}

/**
 * 检测网络是否连接
 */
fun Context.isNetworkConnected(): Boolean {
	val connectionManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
	val networkInfo = connectionManager.activeNetworkInfo
	return networkInfo != null && networkInfo.isAvailable && networkInfo.isConnected
}


/**
 * 检测移动网络是否连接
 */
@Suppress("DEPRECATION")
fun Context.isMobileConnected(): Boolean {
	val connectionManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
	val networkInfo = connectionManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
	return networkInfo != null && networkInfo.isAvailable && networkInfo.isConnected
}

/**
 * 检测Wifi网络是否连接
 */
@Suppress("DEPRECATION")
fun Context.isWifiConnected(): Boolean {
	val connectionManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
	val networkInfo = connectionManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
	return networkInfo != null && networkInfo.isAvailable && networkInfo.isConnected
}