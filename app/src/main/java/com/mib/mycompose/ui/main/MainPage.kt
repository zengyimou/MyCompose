package com.mib.mycompose.ui.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Switch
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
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
import com.mib.mycompose.ui.theme.C_30B284
import com.mib.mycompose.ui.theme.C_666666
import com.mib.mycompose.ui.theme.C_F86161
import com.mib.mycompose.ui.theme.C_F8F8F8
import com.mib.mycompose.ui.theme.FF999999
import com.mib.mycompose.ui.theme.White
import com.mib.mycompose.ui.widget.CircleRing
import com.mib.mycompose.ui.widget.TextWithEndIcon
import com.mib.mycompose.util.Logger

/**
 *  author : cengyimou
 *  date : 2024/4/11 10:31
 *  description :
 */
@Preview
@Composable
fun MainPage(mainPageViewModel: MainPageViewModel= viewModel()){
	Logger.d("MainComponent","MainPage ${mainPageViewModel.hashCode()}")
	/** 是否显示Sip提醒模块*/
	var isShowSipControl by remember { mutableStateOf(true) }
	/** 是否显示sip提醒文案*/
	var isShowSipTips by remember { mutableStateOf(true) }
	/** Sip按钮状态*/
	var switchIsCheck by remember { mutableStateOf(false) }

	val context = LocalContext.current

	ConstraintLayout(
		modifier = Modifier
			.fillMaxWidth()
			.fillMaxHeight()
			.background(White),
	) {
		val ( tvHello, tvSoContent, clSipControl ) = createRefs()

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
			pointOfYearPercent = 70f,
			content = "70%",
			label = "Collected amount rate"
		)

	}
}

@Composable
fun ClSipControl(modifier: Modifier = Modifier, checked: Boolean = true,
                 isShowTips: Boolean, checkListener: (Boolean) -> Unit = {}){
	ConstraintLayout(
		modifier = modifier
			.background(
				color = C_F8F8F8,
				shape = RoundedCornerShape(12.dp)
			)
			.padding(horizontal = 16.dp, vertical = 12.dp),
	) {
		val (
			tvTitle, tvTips, ivSipTips, switchSip,
		) = createRefs()
		Image(
			modifier = Modifier
				.constrainAs(ivSipTips) {
					top.linkTo(parent.top)
					start.linkTo(parent.start)
					bottom.linkTo(parent.bottom)
				}
				.width(20.dp)
				.height(20.dp),
			painter = painterResource(id = R.mipmap.ic_deline),
			contentDescription = null
		)

		Text(
			text = "Fuera de linea",
			color = C_111111,
			fontSize = 14.sp,
			modifier = Modifier
				.constrainAs(tvTitle) {
					top.linkTo(parent.top)
					start.linkTo(ivSipTips.end, 10.dp)
					end.linkTo(switchSip.start)
					width = Dimension.fillToConstraints
					//height = Dimension.wrapContent
				}
		)
		if(isShowTips){
			Text(
				text = "SIP isn’t connected, please log in again",
				color = C_F86161,
				fontSize = 10.sp,
				modifier = Modifier
					.constrainAs(tvTips) {
						top.linkTo(tvTitle.bottom, 4.dp)
						start.linkTo(tvTitle.start)
						end.linkTo(switchSip.start)
						bottom.linkTo(parent.bottom)
						width = Dimension.fillToConstraints
						//height = Dimension.wrapContent
					}
			)
		}

		Switch(checked = checked, onCheckedChange = {
			checkListener.invoke(it)
		},
			Modifier
				.constrainAs(switchSip) {
					top.linkTo(parent.top)
					end.linkTo(parent.end)
					bottom.linkTo(parent.bottom)
					width = Dimension.wrapContent
				}
				.height(20.dp))

	}
}