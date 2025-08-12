package com.mib.mycompose.util

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 *  author : cengyimou
 *  date : 2025/7/24 12:31
 *  description :
 */
@Immutable
data class AppSpacing(
    val small: Dp = 4.dp,
    val medium: Dp = 8.dp,
    val large: Dp = 16.dp,
    val xLarge: Dp = 24.dp
)

// 单例提供
val LocalAppSpacing = staticCompositionLocalOf { AppSpacing() }

@Composable
fun ProvideSpacing(content: @Composable () -> Unit) {
    CompositionLocalProvider(LocalAppSpacing provides AppSpacing()) {
        content()
    }
}