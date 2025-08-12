package com.mib.mycompose.util

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

/**
 *  author : cengyimou
 *  date : 2025/7/24 15:48
 *  description :
 */
@Composable
fun AnimatedGoneView(
    isVisible: Boolean,
    content: @Composable () -> Unit
) {
    AnimatedVisibility(
        visible = isVisible,
        enter = expandVertically(animationSpec = tween(500)),
        exit = shrinkVertically(animationSpec = tween(500))
    ) {
        Box(Modifier.animateContentSize()) {
            content()
        }
    }
}