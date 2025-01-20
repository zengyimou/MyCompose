package com.mib.mycompose.ui.widget

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally

/**
 *  author : cengyimou
 *  date : 2025/1/15 17:05
 *  description :
 */


// 自定义动画
@OptIn(ExperimentalAnimationApi::class)
fun test1(): EnterTransition = slideInHorizontally(
    initialOffsetX = { fullWidth -> fullWidth }, // 从右侧进入
    animationSpec = tween(durationMillis = 300)
)
@OptIn(ExperimentalAnimationApi::class)
fun defaultExitTransition(): ExitTransition = slideOutHorizontally(
    targetOffsetX = { fullWidth -> -fullWidth }, // 向左退出
    animationSpec = tween(durationMillis = 300)
)
@OptIn(ExperimentalAnimationApi::class)
fun defaultPopEnterTransition(): EnterTransition = slideInHorizontally(
    initialOffsetX = { fullWidth -> -fullWidth }, // 从左侧返回进入
    animationSpec = tween(durationMillis = 300)
)
@OptIn(ExperimentalAnimationApi::class)
fun defaultPopExitTransition(): ExitTransition = slideOutHorizontally(
    targetOffsetX = { fullWidth -> fullWidth }, // 向右返回退出
    animationSpec = tween(durationMillis = 300)
)