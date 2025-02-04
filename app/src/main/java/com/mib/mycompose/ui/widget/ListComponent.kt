package com.mib.mycompose.ui.widget

/**
 *  author : cengyimou
 *  date : 2024/4/18 15:00
 *  description :
 */
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults.elevation
import androidx.compose.material.ButtonDefaults.textButtonColors
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.mib.mycompose.R
import com.mib.mycompose.ui.theme.gray300
import com.mib.mycompose.ui.theme.gray600
import com.mib.mycompose.ui.theme.gray700
import kotlinx.coroutines.launch

/**
 * 下拉加载封装
 *
 * implementation "com.google.accompanist:accompanist-swiperefresh:xxx"
 * */
@Composable
fun <T : Any> SwipeRefreshList(
	modifier: Modifier = Modifier,
	collectAsLazyPagingItems: LazyPagingItems<T>,
	listContent: LazyListScope.() -> Unit,
) {
	val rememberSwipeRefreshState = rememberSwipeRefreshState(isRefreshing = false)

	SwipeRefresh(
		modifier = modifier,
		state = rememberSwipeRefreshState,
		onRefresh = { collectAsLazyPagingItems.refresh() }
	) {
		val lazyListState = rememberLazyListState()

		val coroutineScope = rememberCoroutineScope()

		rememberSwipeRefreshState.isRefreshing =
			collectAsLazyPagingItems.loadState.refresh is LoadState.Loading

		LazyColumn(
			state = lazyListState,
			modifier = Modifier
				.fillMaxWidth()
				.fillMaxHeight(),

			) {
			listContent()
			collectAsLazyPagingItems.apply {
				when {
					loadState.append is LoadState.Loading -> {
						//加载更多，底部loading
						item { LoadingItem() }
					}
					loadState.append is LoadState.Error -> {
						//加载更多异常
						item {
							ErrorMoreRetryItem() {
								collectAsLazyPagingItems.retry()
							}
						}
					}

					loadState.append == LoadState.NotLoading(endOfPaginationReached = true) -> {
						// 已经没有更多数据了
						item {
							NoMoreDataFindItem(onClick = {
								coroutineScope.launch {
									lazyListState.animateScrollToItem(0)
								}
							})
						}
					}

					loadState.refresh is LoadState.Error -> {
						if (collectAsLazyPagingItems.itemCount <= 0) {
							//刷新的时候，如果itemCount小于0，第一次加载异常
							item {
								ErrorContent() {
									collectAsLazyPagingItems.retry()
								}
							}
						} else {
							item {
								ErrorMoreRetryItem() {
									collectAsLazyPagingItems.retry()
								}
							}
						}
					}
					loadState.refresh is LoadState.Loading -> {
						// 第一次加载且正在加载中
						if (collectAsLazyPagingItems.itemCount == 0) {
						}
					}
				}
			}
		}
	}
}

/**
 * 底部加载更多失败处理
 * */
@Composable
fun ErrorMoreRetryItem(retry: () -> Unit) {
	Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
		TextButton(
			onClick = { retry() },
			modifier = Modifier
				.padding(20.dp)
				.width(80.dp)
				.height(30.dp),
			shape = RoundedCornerShape(6.dp),
			contentPadding = PaddingValues(3.dp),
			colors = textButtonColors(backgroundColor = gray300),
			elevation = elevation(
				defaultElevation = 2.dp,
				pressedElevation = 4.dp,
			),
		) {
			Text(text = "请重试", color = gray600)
		}
	}
}

@Composable
fun NoMoreDataFindItem(onClick: () -> Unit= {}) {
	Box(modifier = Modifier.fillMaxWidth().padding(16.dp), contentAlignment = Alignment.Center) {
		Text(text = "已经没有更多数据啦 ~~ Click to top", color = gray600)
	}
}


/**
 * 页面加载失败处理
 * */
@Composable
fun ErrorContent(retry: () -> Unit) {
	Column(
		modifier = Modifier
			.fillMaxSize()
			.padding(top = 100.dp),
		verticalArrangement = Arrangement.Center,
		horizontalAlignment = Alignment.CenterHorizontally
	) {
		Image(
			modifier = Modifier.padding(top = 80.dp),
			painter = painterResource(id = R.drawable.icon_empty),
			contentDescription = null
		)
		Text(text = "No Data", modifier = Modifier.padding(8.dp))
		TextButton(
			onClick = { retry() },
			modifier = Modifier
				.padding(20.dp)
				.width(80.dp)
				.height(30.dp),
			shape = RoundedCornerShape(10.dp),
			contentPadding = PaddingValues(5.dp),
			colors = textButtonColors(backgroundColor = gray300),
			elevation = elevation(
				defaultElevation = 2.dp,
				pressedElevation = 4.dp,
			)
			//colors = ButtonDefaults
		) { Text(text = "重试", color = gray700) }
	}
}

/**
 * 底部加载更多正在加载中...
 * */
@Composable
fun LoadingItem() {
	Row(
		modifier = Modifier
			.height(34.dp)
			.fillMaxWidth()
			.padding(5.dp),
		horizontalArrangement = Arrangement.Center
	) {
		CircularProgressIndicator(
			modifier = Modifier
				.size(24.dp),
			color = gray600,
			strokeWidth = 2.dp
		)
		Text(
			text = "加载中...",
			color = gray600,
			modifier = Modifier
				.fillMaxHeight()
				.padding(start = 20.dp),
			fontSize = 18.sp,
		)
	}
}