package com.mib.mycompose.ui.widget

import android.os.Build
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.mib.mycompose.util.StatusBarUtils.statusBarHeightPx

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

/**
 * 设置自定义沉浸式状态栏
 * @receiver ComponentActivity
 * @param statusBarColor Int
 */
fun ComponentActivity.enableCustomEdgeToEdge(statusBarColor: Int = android.graphics.Color.TRANSPARENT){
    enableEdgeToEdge()

    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q){
        window.isNavigationBarContrastEnforced = false
    }

    window.statusBarColor = statusBarColor

    ViewCompat.setOnApplyWindowInsetsListener(window.decorView){ view, insets ->
        val navBarInsets = insets.getInsets(WindowInsetsCompat.Type.navigationBars())
        val statusBarInsets = insets.getInsets(WindowInsetsCompat.Type.statusBars())
        statusBarHeightPx =  statusBarInsets.top

        view.setPadding(0, 0, 0, navBarInsets.bottom)

        WindowInsetsCompat.CONSUMED
    }
}

@Composable
fun Modifier.statusBarPadding(): Modifier {
    val density = LocalDensity.current
    val statusBarHeight = with(density) { statusBarHeightPx.toDp() }
    return this.padding(top = statusBarHeight)
}