package com.mib.mycompose.ui.main

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mib.mycompose.R
import com.mib.mycompose.ext.toast
import com.mib.mycompose.ui.theme.C_111111
import com.mib.mycompose.ui.theme.C_1A18FFAD
import com.mib.mycompose.ui.theme.C_1AFF6C1D
import com.mib.mycompose.ui.theme.C_30B284
import com.mib.mycompose.ui.theme.C_666666
import com.mib.mycompose.ui.theme.FF999999
import com.mib.mycompose.ui.theme.White
import com.mib.mycompose.ui.widget.CircleRing
import com.mib.mycompose.ui.widget.ClSipControl
import com.mib.mycompose.ui.widget.InfoItem
import com.mib.mycompose.ui.widget.MainPageColorItem
import com.mib.mycompose.ui.widget.MainPageTab
import com.mib.mycompose.ui.widget.TextWithEndIcon
import com.mib.mycompose.util.Logger

/**
 *  author : cengyimou
 *  date : 2024/4/11 10:31
 *  description :
 */
@Preview
@Composable
fun MainPage(modifier: Modifier = Modifier, mainPageViewModel: MainPageViewModel= viewModel()){
	Logger.d("MainComponent","MainPage ${mainPageViewModel.hashCode()}")
	/** 是否显示Sip提醒模块*/
	var isShowSipControl by remember { mutableStateOf(true) }
	/** 是否显示sip提醒文案*/
	var isShowSipTips by remember { mutableStateOf(true) }
	/** Sip按钮状态*/
	var switchIsCheck by remember { mutableStateOf(false) }

	val context = LocalContext.current

	ConstraintLayout(
		modifier = modifier
			.fillMaxWidth()
			.fillMaxHeight()
			.background(White)
			.verticalScroll(rememberScrollState()),
	) {
		val ( tvHello, tvSoContent, clSipControl, bottomSpacer ) = createRefs()

		TextWithEndIcon(
			text= "hello,AAA",
			modifier = Modifier
				.constrainAs(tvHello) {
					top.linkTo(parent.top, 8.dp)
					start.linkTo(parent.start, 16.dp)
					end.linkTo(parent.end, 16.dp)
					width = Dimension.fillToConstraints
				},
			fontSize = 18.sp,
			fontWeight = FontWeight.Bold,
			res = R.mipmap.icon_emoji_hello,
		)

		Text(
			text = "Keep up the good work!",
			color = C_666666,
			fontSize = 12.sp,
			modifier = Modifier
				.constrainAs(tvSoContent) {
					top.linkTo(tvHello.bottom, 4.dp)
					start.linkTo(tvHello.start)
					end.linkTo(tvHello.end)
					width = Dimension.fillToConstraints
				}
		)
		/** Sip提醒模块*/
		if(isShowSipControl){
			ClSipControl(
				checked = switchIsCheck,
				isShowTips = isShowSipTips,
				modifier = Modifier.constrainAs(clSipControl){
					top.linkTo(tvSoContent.bottom, 15.dp)
					start.linkTo(tvHello.start)
					end.linkTo(parent.end, 16.dp)
					width = Dimension.fillToConstraints
				}
			){ check ->
				switchIsCheck = check
				context.toast("check ${check}")
			}
		}
		val ( tvPerformanceTitle, tvSeeMore, tvLastUpdateTime, circleRing ) = createRefs()
		Text(
			text = "Today's Performance",
			color = C_111111,
			fontSize = 24.sp,
			style = TextStyle(
				fontWeight = FontWeight.Bold,
			),
			modifier = Modifier
				.constrainAs(tvPerformanceTitle) {
					top.linkTo(clSipControl.bottom, 32.dp)
					start.linkTo(parent.start, 16.dp)
					end.linkTo(tvSeeMore.start)
					width = Dimension.fillToConstraints
				}
		)

		Text(
			text = "See more",
			color = C_30B284,
			fontSize = 12.sp,
			modifier = Modifier
				.clickable {
					context.toast("see more click")
				}
				.constrainAs(tvSeeMore) {
					top.linkTo(tvPerformanceTitle.top)
					bottom.linkTo(tvPerformanceTitle.bottom)
					start.linkTo(tvPerformanceTitle.end, 16.dp)
					end.linkTo(parent.end, 16.dp)
					width = Dimension.wrapContent
				}
		)

		Text(
			text = "Latest update time 12:30",
			color = FF999999,
			fontSize = 12.sp,
			modifier = Modifier
				.clickable {
					context.toast("see more click")
				}
				.constrainAs(tvLastUpdateTime) {
					top.linkTo(tvPerformanceTitle.bottom, 8.dp)
					start.linkTo(tvPerformanceTitle.start)
					width = Dimension.wrapContent
				}
		)

		CircleRing(
			modifier = Modifier.constrainAs(circleRing) {
				top.linkTo(tvLastUpdateTime.bottom, 25.dp)
				start.linkTo(parent.start, 16.dp)
				end.linkTo(parent.end, 16.dp)
				width = Dimension.wrapContent
			},
			width = 240,
			pointOfYearPercent = 70F,
			content = "70%",
			label = "Collected amount rate"
		)

		val ( mainPageCollectCase, mainPageCollectAmount ) = createRefs()
		MainPageColorItem(
			iconRes = R.mipmap.icon_collect_case,
			title = "Collected cases",
			content = "80",
			description = "Total: 120",
			modifier = Modifier
				.constrainAs(mainPageCollectCase) {
					top.linkTo(circleRing.bottom, 25.dp)
					start.linkTo(parent.start, 16.dp)
					end.linkTo(mainPageCollectAmount.start, 8.dp)
					width = Dimension.fillToConstraints
				}
				.background(color = C_1A18FFAD, shape = RoundedCornerShape(12.dp))
		)

		MainPageColorItem(
			iconRes = R.mipmap.icon_collect_amount,
			title = "Collected  amount",
			content = "123123123123",
			description = "Total: 123123123123",
			modifier = Modifier
				.constrainAs(mainPageCollectAmount) {
					top.linkTo(mainPageCollectCase.top)
					start.linkTo(mainPageCollectCase.end)
					end.linkTo(parent.end, 16.dp)
					width = Dimension.fillToConstraints
				}
				.background(color = C_1AFF6C1D, shape = RoundedCornerShape(12.dp))
		)

		val ( infoItemCallCount, infoItemCallCases, infoItemCallDuration,
			infoItemSendMessage, infoItemOnlineTime, infoItemGroupRanking ) = createRefs()

		InfoItem(
			title = context.getString(R.string.main_collect_call_counting),
			content = "123",
			modifier = Modifier.constrainAs(infoItemCallCount){
				top.linkTo(mainPageCollectAmount.bottom, 16.dp)
				start.linkTo(mainPageCollectCase.start)
				end.linkTo(infoItemCallCases.start, 8.dp)
				width = Dimension.fillToConstraints
			}
		)

		InfoItem(
			title = context.getString(R.string.main_collect_call_cases),
			content = "123",
			modifier = Modifier.constrainAs(infoItemCallCases){
				top.linkTo(infoItemCallCount.top)
				start.linkTo(infoItemCallCount.end)
				end.linkTo(infoItemCallDuration.start, 8.dp)
				width = Dimension.fillToConstraints
			}
		)

		InfoItem(
			title = context.getString(R.string.main_collect_call_duration),
			content = "123",
			isRankOne = true,
			modifier = Modifier.constrainAs(infoItemCallDuration){
				top.linkTo(infoItemCallCount.top)
				start.linkTo(infoItemCallCases.end)
				end.linkTo(parent.end, 16.dp)
				width = Dimension.fillToConstraints
			}
		)

		InfoItem(
			title = context.getString(R.string.main_collect_send_message),
			content = "123",
			modifier = Modifier.constrainAs(infoItemSendMessage){
				top.linkTo(infoItemCallCount.bottom, 8.dp)
				start.linkTo(mainPageCollectCase.start)
				end.linkTo(infoItemOnlineTime.start, 8.dp)
				width = Dimension.fillToConstraints
			}
		)

		InfoItem(
			title = context.getString(R.string.main_collect_onlinetime),
			content = "123",
			modifier = Modifier.constrainAs(infoItemOnlineTime){
				top.linkTo(infoItemSendMessage.top)
				start.linkTo(infoItemSendMessage.end)
				end.linkTo(infoItemGroupRanking.start, 8.dp)
				width = Dimension.fillToConstraints
			}
		)

		InfoItem(
			title = context.getString(R.string.main_collect_group_ranking),
			content = "123",
			isRankOne = true,
			modifier = Modifier.constrainAs(infoItemGroupRanking){
				top.linkTo(infoItemSendMessage.top)
				start.linkTo(infoItemOnlineTime.end)
				end.linkTo(parent.end, 16.dp)
				width = Dimension.fillToConstraints
			}
		)

		val ( tvCaseData, tabContent, columnCaseData ) = createRefs()
		Text(
			text = "Today's case data",
			color = C_111111,
			fontSize = 24.sp,
			style = TextStyle(
				fontWeight = FontWeight.Bold,
			),
			modifier = Modifier
				.constrainAs(tvCaseData) {
					top.linkTo(infoItemGroupRanking.bottom, 32.dp)
					start.linkTo(parent.start, 16.dp)
					end.linkTo(parent.end)
					width = Dimension.fillToConstraints
				}
		)
		val caseDataList = mainPageViewModel.caseDataList.observeAsState()
		MainPageTab(modifier =  Modifier.constrainAs(tabContent){
			top.linkTo(tvCaseData.bottom, 16.dp)
			start.linkTo(parent.start, 16.dp)
			end.linkTo(parent.end, 16.dp)
			width = Dimension.fillToConstraints
		}){ selectIndex ->
			mainPageViewModel.clickMainPageTab(index = selectIndex)
		}
		Column(
			modifier = Modifier.constrainAs(columnCaseData){
				top.linkTo(tabContent.bottom, 16.dp)
				start.linkTo(parent.start, 16.dp)
				end.linkTo(parent.end, 16.dp)
			}
		) {
			caseDataList.value?.forEach{
				Text(text = it.eventTitle?: "")
			}
		}

	}
}
