package com.mib.mycompose.ui.widget

import android.app.Activity
import android.app.StatusBarManager
import androidx.activity.ComponentActivity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.mib.mycompose.util.StatusBarUtils
import com.mib.mycompose.util.enableCustomEdgeToEdge

/**
 *  author : cengyimou
 *  date : 2025/1/8 11:25
 *  description :
 */
@Composable
fun SetStatusBarColor(color: Color, darkIcons: Boolean) {
    val activity = LocalContext.current as ComponentActivity
    // 设置状态栏颜色
    SideEffect {
        activity.window?.let {
            it.statusBarColor = color.toArgb()
            WindowInsetsControllerCompat(it, it.decorView).isAppearanceLightStatusBars = darkIcons
        }
    }
}

@Composable
fun SetEdgeToEdge(color: Color, darkIcons: Boolean) {
    val activity = LocalContext.current as ComponentActivity
    activity.enableCustomEdgeToEdge(color.toArgb())

    activity?.window?.let {
        WindowCompat.setDecorFitsSystemWindows(it, false)
        WindowCompat.getInsetsController(it, it.decorView).isAppearanceLightStatusBars = darkIcons
    }
}