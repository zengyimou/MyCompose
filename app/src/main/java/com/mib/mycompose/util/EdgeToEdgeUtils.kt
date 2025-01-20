package com.mib.mycompose.util

import android.app.Activity
import android.graphics.Color
import android.os.Build
import android.view.View
import android.view.Window
import android.view.WindowInsetsController
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import androidx.compose.ui.unit.Dp
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import com.mib.mycompose.util.StatusBarUtils.statusBarHeightPx

/**
 *  author : cengyimou
 *  date : 2025/1/8 11:57
 *  description :
 *
 *
 */
object StatusBarUtils {

    /**
     * 设置状态栏黑色字体
     */
    fun setStatusBarLightMode(activity: Activity?) {

        activity?.window?.let {
            WindowCompat.setDecorFitsSystemWindows(it, false)
            WindowCompat.getInsetsController(it, it.decorView).isAppearanceLightStatusBars = true
        }
//        activity?.window?.let { setStatusBarColor(window = it, isLightFont = false) }
    }

    /**
     * 设置状态栏白色字体
     */
    fun setStatusBarDarkMode(activity: Activity?) {
        activity?.window?.let { setStatusBarColor(window = it, isLightFont = true) }
    }


    /**
     * 设置状态栏颜色模式
     * @param window Window
     * @param isLightFont Boolean 是否浅色
     */
    private fun setStatusBarColor(window: Window, isLightFont: Boolean) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            // 使用 WindowInsetsController
            val controller = window.insetsController
            if (isLightFont) {
                controller?.setSystemBarsAppearance(
                    0,
                    WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
                )
            } else {
                controller?.setSystemBarsAppearance(
                    WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS,
                    WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
                )
            }
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // 使用 SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            if (isLightFont) {
                window.decorView.systemUiVisibility =
                    window.decorView.systemUiVisibility and
                            View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR.inv()
            } else {
                window.decorView.systemUiVisibility =
                    window.decorView.systemUiVisibility or
                            View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            }
        } else {
            // Android 5.0 以下无法直接修改字体颜色，只能更改背景颜色
            if (isLightFont) {
                window.statusBarColor = Color.BLACK // 深色背景
            } else {
                window.statusBarColor = Color.WHITE // 浅色背景
            }
        }
    }
    var statusBarHeightPx =  0
}
