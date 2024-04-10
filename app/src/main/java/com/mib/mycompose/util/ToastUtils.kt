package com.mib.mycompose.util

import android.annotation.SuppressLint
import android.content.Context
import android.widget.Toast
import androidx.annotation.StringRes
import com.mib.mycompose.base.BaseApplication

/**
 *  author : cengyimou
 *  date : 2024/4/9 10:26
 *  description :
 */
object ToastUtils {

	private var toast: Toast? = null

	@SuppressLint("ShowToast")
	private fun initToast(message: CharSequence, duration: Int): Toast {
		if (toast == null) {
			toast = Toast.makeText(BaseApplication.appContext, message, duration)
		} else {
			toast!!.setText(message)
			toast!!.duration = duration
		}
		return toast!!
	}

	/**
	 * 短时间显示Toast
	 *
	 * @param message
	 */
	fun showShort(message: CharSequence?) {
		if (message.isNullOrEmpty() || message.toString() == "null") {
			return
		}
		initToast(message, Toast.LENGTH_SHORT).show()
	}


	/**
	 * 短时间显示Toast
	 *
	 * @param strResId
	 */
	fun showShort(@StringRes strResId: Int) {
		showShort(BaseApplication.appContext.resources.getText(strResId))
	}

	/**
	 * 长时间显示Toast
	 *
	 * @param message
	 */
	fun showLong(message: CharSequence?) {
		if (message.isNullOrEmpty() || message.toString() == "null") {
			return
		}
		initToast(message, Toast.LENGTH_LONG).show()
	}

	/**
	 * 长时间显示Toast
	 *
	 * @param strResId
	 */
	fun showLong(@StringRes strResId: Int) {
		showLong(BaseApplication.appContext.resources.getText(strResId))
	}

	/**
	 * 自定义显示Toast时间
	 *
	 * @param message
	 * @param duration
	 */
	fun show(message: CharSequence?, duration: Int) {
		if (message.isNullOrEmpty() || message.toString() == "null") {
			return
		}
		initToast(message, duration).show()
	}

	/**
	 * 自定义显示Toast时间
	 *
	 * @param context
	 * @param strResId
	 * @param duration
	 */
	fun show(context: Context, @StringRes strResId: Int, duration: Int) {
		show(context.resources.getText(strResId), duration)
	}

}