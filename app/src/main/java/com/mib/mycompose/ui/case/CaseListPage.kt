package com.mib.mycompose.ui.case

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.jeremyliao.liveeventbus.LiveEventBus
import com.mib.mycompose.event.Event
import com.mib.mycompose.event.LogoutEvent
import com.mib.mycompose.ext.toast
import com.mib.mycompose.model.NewCaseListItem
import com.mib.mycompose.ui.theme.C_FF000000
import com.mib.mycompose.ui.theme.C_Main
import com.mib.mycompose.ui.widget.DropDownTab
import com.mib.mycompose.util.Logger

/**
 *  author : cengyimou
 *  date : 2024/4/17 11:25
 *  description :
 */
@Preview
@Composable
fun CaseListPage(
	modifier: Modifier = Modifier,
	navHostController: NavHostController = rememberNavController(),
	caseListViewModel: CaseViewModel = viewModel()
) {
	/** select tab的选择状态*/
	var taskSelectState by rememberSaveable { mutableStateOf(false) }
	var intentionalSelectState by rememberSaveable { mutableStateOf(false) }
	var caseTypeSelectState by rememberSaveable { mutableStateOf(false) }

	val caseListLiveData = caseListViewModel.caseList.observeAsState(listOf<NewCaseListItem>())

	val lazyListState = rememberLazyListState()
	val coroutineScope = rememberCoroutineScope()

	var firstInit by rememberSaveable { mutableStateOf(true)}
	val context = LocalContext.current

	/** 下拉刷新*/
	val isRefreshing = caseListViewModel.isRefreshing.observeAsState()
	val rememberSwipeRefreshState = rememberSwipeRefreshState(isRefreshing = false)
	rememberSwipeRefreshState.isRefreshing = isRefreshing.value == true
	/** 分页*/
	val noMoreData = caseListViewModel.noMoreData.observeAsState()

	LaunchedEffect(Unit){
		if(firstInit){
			caseListViewModel.getCaseList(isRefresh = true)
			firstInit = false
		}
	}
	
	Column(
		modifier = modifier
			.fillMaxWidth()
			.fillMaxHeight(),
		verticalArrangement = Arrangement.Top
	) {
		Row(
			modifier = Modifier
				.fillMaxWidth()
				.heightIn(44.dp, 64.dp)
				.background(color = C_Main)
				.wrapContentWidth()
		) {
			DropDownTab(
				modifier = Modifier
					.weight(weight = 1f)
					.fillMaxHeight(),
				tabText = "Task Status",
				isSelected = taskSelectState,
			) {
				taskSelectState = !taskSelectState
			}

			DropDownTab(
				modifier = Modifier
					.weight(weight = 1f)
					.fillMaxHeight(),
				tabText = "Intentional Status",
				isSelected = intentionalSelectState
			) {
				intentionalSelectState = !intentionalSelectState
			}

			DropDownTab(
				modifier = Modifier
					.weight(weight = 1f)
					.fillMaxHeight(),
				tabText = "Case Type",
				isSelected = caseTypeSelectState
			) {
				caseTypeSelectState = !caseTypeSelectState
			}

		}

		SwipeRefresh(
			modifier = Modifier
				.fillMaxWidth()
				.fillMaxHeight()
				.background(color = Color.White),
			state = rememberSwipeRefreshState,
			onRefresh = {
				caseListViewModel.getCaseList(isRefresh = true)
			}
		) {
			LazyColumn(
				state = lazyListState,
				modifier = Modifier
					.fillMaxWidth()
					.fillMaxHeight(),
			) {
				itemsIndexed(items = caseListLiveData.value) { index, item ->
					Text(
						modifier = Modifier.height(100.dp),
						text = "text $index",
						fontSize = 14.sp,
						color = C_FF000000,
					)
				}
				if(noMoreData.value == false){
					item {
						CircularProgressIndicator(modifier = Modifier.padding(16.dp), color = C_Main)
						LaunchedEffect(Unit) {
							caseListViewModel.getCaseList(isRefresh = false)
						}
					}
				}else {
					item {
						Text(
							modifier = Modifier.height(20.dp).fillMaxWidth(),
							text = "No More Data",
							fontSize = 14.sp,
							color = C_FF000000,
						)
					}
				}
			}
		}

	}
}