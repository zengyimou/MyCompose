package com.mib.mycompose.ui.widget

import androidx.annotation.FloatRange
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.pager.PagerState
import androidx.compose.material.MaterialTheme
import androidx.compose.material.TabPosition
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.debugInspectorInfo
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.lerp
import com.mib.mycompose.ui.theme.C_Main
import org.w3c.dom.Text
import kotlin.math.abs

/**
 *  author : cengyimou
 *  date : 2024/4/17 16:54
 *  description :
 */
fun Modifier.tabIndicatorOffset(
	text: Text,
	currentTabPosition: TabPosition
): Modifier = composed(
	inspectorInfo = debugInspectorInfo {
		name = "tabIndicatorOffset"
		value = currentTabPosition
	}
) {
	val currentTabWidth by animateDpAsState(
		targetValue = currentTabPosition.width,
		animationSpec = tween(durationMillis = 250, easing = FastOutSlowInEasing)
	)
	val indicatorOffset by animateDpAsState(
		targetValue = currentTabPosition.left,
		animationSpec = tween(durationMillis = 250, easing = FastOutSlowInEasing)
	)
	fillMaxWidth()
		.wrapContentSize(Alignment.BottomStart)
		.offset(x = indicatorOffset)
		.width(currentTabWidth)
}

/**
 * PagerTap 指示器
 * @param  percent  指示器占用整个tab宽度的比例
 * @param  height   指示器的高度
 * @param  color    指示器的颜色
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PagerTabIndicator(
	tabPositions: List<TabPosition>,
	pagerState: PagerState,
	color: Color = C_Main,
	@FloatRange(from = 0.0, to = 1.0) percent: Float = 0.4f,
	height: Dp = 4.dp,
) {
	Canvas(modifier = Modifier.fillMaxSize()) {
		val currentPage = minOf(tabPositions.lastIndex, pagerState.currentPage)
		val currentTab = tabPositions[currentPage]
		val previousTab = tabPositions.getOrNull(currentPage - 1)
		val nextTab = tabPositions.getOrNull(currentPage + 1)
		val fraction = pagerState.currentPageOffsetFraction

		val indicatorWidth = currentTab.width.toPx() * percent

		val indicatorOffset = if (fraction > 0 && nextTab != null) {
			lerp(currentTab.left, nextTab.left, fraction).toPx()
		} else if (fraction < 0 && previousTab != null) {
			lerp(currentTab.left, previousTab.left, -fraction).toPx()
		} else {
			currentTab.left.toPx()
		}

		/*Log.i(
			"hj",
			"fraction = ${fraction} , indicatorOffset = ${indicatorOffset}"
		)*/
		val canvasHeight = size.height
		drawRoundRect(
			color = color,
			topLeft = Offset(
				indicatorOffset + (currentTab.width.toPx() * (1 - percent) / 2),
				canvasHeight - height.toPx()
			),
			size = Size(indicatorWidth + indicatorWidth * abs(fraction), height.toPx()),
			cornerRadius = CornerRadius(50f)
		)
	}
}
