package com.mib.mycompose.ui.case

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.TabRowDefaults
import androidx.compose.material.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.mib.mycompose.constants.C.LINK_TAG
import com.mib.mycompose.ui.theme.C_111111
import com.mib.mycompose.ui.theme.C_Main
import com.mib.mycompose.util.Logger
import kotlinx.coroutines.launch

/**
 *  author : cengyimou
 *  date : 2024/4/11 10:32
 *  description :
 */
@OptIn(ExperimentalFoundationApi::class)
@Preview
@Composable
fun CasePage(modifier: Modifier = Modifier, navHostController: NavHostController = rememberNavController()) {
	val isShow by remember { mutableStateOf(false) }
	val pagerState = rememberPagerState(
		initialPage = 0,
		initialPageOffsetFraction = 0f,
	) { 2 }
	val scope = rememberCoroutineScope()
	Logger.d(LINK_TAG, "CasePage code ${navHostController.hashCode()}")
	val pages = listOf("UnPaid cases", "Paid cases")
	Column(
		modifier = modifier
			.fillMaxWidth()
			.fillMaxHeight()
			.background(color = Color.White)
	) {
		TabRow(
			modifier = Modifier
				.fillMaxWidth()
				.height(50.dp),
			selectedTabIndex = pagerState.currentPage,
			backgroundColor = Color.White,
			indicator = { tabPositions ->

				val currentTabPosition = tabPositions[0]
				//修改指示器长度
				val currentTabWidth by animateDpAsState(
					targetValue = currentTabPosition.width / 2,
					animationSpec = tween(durationMillis = 250, easing = FastOutSlowInEasing), label = ""
				)
				//修改指示器偏移量为居中
				val indicatorOffset by animateDpAsState(
					targetValue = currentTabPosition.left + (currentTabPosition.width / 2 - currentTabWidth / 2),
					animationSpec = tween(durationMillis = 250, easing = FastOutSlowInEasing), label = ""
				)
				TabRowDefaults.Indicator(
					modifier = Modifier.
					tabIndicatorOffset(tabPositions[pagerState.currentPage])
						.fillMaxWidth()
						.wrapContentSize(Alignment.BottomStart)
						.offset(x = indicatorOffset)
						.width(currentTabWidth)
						.height(2.dp),
					color = C_Main,
				)
			}
		) {
			// Add tabs for all of our pages
			pages.forEachIndexed { index, title ->
				Logger.d("CasePage", "title $title")
				Tab(
					modifier = Modifier
						.wrapContentWidth()
						.fillMaxHeight(),
					text = {
						Text(
							text = title,
							color = C_111111,
							fontSize = 14.sp
						)
					},
					selected = pagerState.currentPage == index,
					onClick = {
						scope.launch {
							pagerState.animateScrollToPage(index)
						}
					},
				)
			}
		}

		HorizontalPager(
			modifier = Modifier
				.fillMaxSize(),
			state = pagerState,
		) { page ->
			CaseListPage(navHostController = navHostController)
		}
	}

//	MainBackHandler()
//	CircularProgressIndicator(isShow = isShow)
}