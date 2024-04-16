package com.mib.mycompose.ui.widget

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Switch
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.mib.mycompose.R
import com.mib.mycompose.ui.theme.C_111111
import com.mib.mycompose.ui.theme.C_F86161
import com.mib.mycompose.ui.theme.C_F8F8F8
import com.mib.mycompose.ui.theme.C_FFF1F1F1
import com.mib.mycompose.ui.theme.C_FFFFEC9E
import com.mib.mycompose.ui.theme.C_Main
import com.mib.mycompose.ui.theme.FF222222
import com.mib.mycompose.ui.theme.FF999999

/**
 *  author : cengyimou
 *  date : 2024/4/15 14:03
 *  description :
 */
@Composable
fun ClSipControl(
	modifier: Modifier = Modifier, checked: Boolean = true,
	isShowTips: Boolean, checkListener: (Boolean) -> Unit = {}
) {
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
		if (isShowTips) {
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

@Composable
fun MainPageColorItem(
	modifier: Modifier = Modifier,
	iconRes: Int = R.mipmap.icon_collect_amount,
	title: String = "",
	content: String = "",
	description: String = ""
) {
	Column(
		modifier = modifier.padding(20.dp),
	) {
		Image(
			painter = painterResource(id = iconRes), contentDescription = null, modifier = Modifier
				.width(32.dp)
				.height(32.dp)
		)
		Text(
			text = title,
			fontSize = 12.sp,
			color = C_111111,
			modifier = Modifier.padding(top = 16.dp)
		)
		Text(
			text = content,
			fontSize = 18.sp,
			color = C_111111,
			fontWeight = FontWeight.Bold,
			modifier = Modifier.padding(top = 6.dp)
		)

		Text(
			text = description,
			fontSize = 12.sp,
			color = FF999999,
			modifier = Modifier.padding(top = 7.dp)
		)
	}
}

@Preview
@Composable
fun InfoItem(
	modifier: Modifier = Modifier,
	title: String = "123123",
	content: String = "12312312",
	isRankOne: Boolean = false
) {
	ConstraintLayout(
		modifier = modifier
			.fillMaxWidth(100f)
			.border(1.dp, color = if (isRankOne) C_FFFFEC9E else C_FFF1F1F1, shape = RoundedCornerShape(8.dp)),
	) {
		val (tvTitle, tvContent, ivCrown) = createRefs()

		Text(
			text = title,
			fontSize = 12.sp,
			color = FF999999,
			modifier = Modifier.constrainAs(tvTitle) {
				start.linkTo(parent.start, 12.dp)
				top.linkTo(parent.top, 12.dp)
				end.linkTo(ivCrown.start, 4.dp)
				width = Dimension.fillToConstraints
			}
		)

		Text(
			text = content,
			fontSize = 18.sp,
			color = C_111111,
			fontWeight = FontWeight.Bold,
			modifier = Modifier.constrainAs(tvContent) {
				start.linkTo(tvTitle.start)
				top.linkTo(tvTitle.bottom, 6.dp)
				end.linkTo(parent.end, 12.dp)
				bottom.linkTo(parent.bottom, 12.dp)
				width = Dimension.fillToConstraints
			}
		)

		if (isRankOne) {
			Image(painter = painterResource(id = R.mipmap.icon_crow_one), contentDescription = null, modifier = Modifier
				.constrainAs(ivCrown) {
					top.linkTo(parent.top)
					end.linkTo(parent.end)
				}
				.width(24.dp)
				.height(24.dp))
		}
	}
}

@Preview
@Composable
fun MainPageTab(
	modifier: Modifier = Modifier,
	selectIndexState: Int = 0,
	listener: (index: Int) -> Unit = {}
) {
//	var selectIndexState by remember { mutableStateOf(0) }
	ConstraintLayout(
		modifier = modifier.fillMaxWidth()
	) {
		val ( tvUnPaid, tvPaid, paidLine, unPaidLine ) = createRefs()
		TextWithStartIcon(
			text = "UnPaid cases",
			fontSize = 14.sp,
			res = R.mipmap.icon_emoji_unpaid,
			color = if(selectIndexState == 0) FF222222 else FF999999,
			modifier =  Modifier.constrainAs(tvUnPaid){
				top.linkTo(parent.top)
				start.linkTo(parent.start)
			}.clickable {
				if(selectIndexState != 0){
					listener.invoke(0)
				}
			}
		)

		TextWithStartIcon(
			text = "Paid cases",
			fontSize = 14.sp,
			res = R.mipmap.icon_emoji_paid,
			color = if(selectIndexState == 1) FF222222 else FF999999,
			modifier =  Modifier.constrainAs(tvPaid){
				top.linkTo(parent.top)
				start.linkTo(tvUnPaid.end, 50.dp)
			}.clickable {
				if(selectIndexState != 1){
					listener.invoke(1)
				}
			}
		)

		Spacer(
			modifier = Modifier.constrainAs(unPaidLine){
				top.linkTo(tvUnPaid.bottom, 3.dp)
				start.linkTo(tvUnPaid.start, 20.dp)
				end.linkTo(tvUnPaid.end, 20.dp)
				width = Dimension.fillToConstraints
			}.background(color = if(selectIndexState == 0) C_Main else Color.Transparent, shape = RoundedCornerShape(1.dp))
				.height(2.dp)
		)

		Spacer(
			modifier = Modifier.constrainAs(paidLine){
				top.linkTo(tvPaid.bottom, 3.dp)
				start.linkTo(tvPaid.start, 20.dp)
				end.linkTo(tvPaid.end, 20.dp)
				width = Dimension.fillToConstraints
			}.background(color = if(selectIndexState == 1) C_Main else Color.Transparent, shape = RoundedCornerShape(1.dp))
				.height(2.dp)
		)
	}
}


@Preview
@Composable
fun TabContentItem(
	modifier: Modifier = Modifier,
	color: Color = Color.Blue,
	title: String = "Case not",
	caseNum: String = "12",
	amount: String = "34",
	itemClickListener: ()-> Unit = {}
){
	ConstraintLayout(
		modifier = modifier.fillMaxWidth().border(
			width = 1.dp,
			shape = RoundedCornerShape(12.dp),
			color = C_FFF1F1F1
		)
	) {
		val ( spacerTop, ivRoundPoint, tvItemTitle, ivArrow, spacerLine,
			tvCaseNum, tvCaseNumLab, tvOutstandingAmount, tvOutstandingAmountLabel ) = createRefs()

		Spacer(
			modifier = Modifier.constrainAs(spacerTop){
				top.linkTo(parent.top)
				start.linkTo(parent.start)
				end.linkTo(parent.end)
				width = Dimension.fillToConstraints
			}.background(color = C_F8F8F8, shape = RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp))
				.height(56.dp).clickable {

				}
		)

		DrawCircle(modifier = Modifier
			.constrainAs(ivRoundPoint) {
				top.linkTo(spacerTop.top)
				bottom.linkTo(spacerTop.bottom)
				start.linkTo(parent.start, 16.dp)
			}, color = color)

		Text(
			text = title,
			fontSize = 14.sp,
			color = C_111111,
			fontWeight = FontWeight.Bold,
			modifier = Modifier.constrainAs(tvItemTitle) {
				start.linkTo(ivRoundPoint.end, 12.dp)
				top.linkTo(ivRoundPoint.top)
				end.linkTo(ivArrow.start, 12.dp)
				bottom.linkTo(ivRoundPoint.bottom)
				width = Dimension.fillToConstraints
			}
		)

		Image(painter = painterResource(id = R.drawable.icon_arrow_right), contentDescription = null, modifier = Modifier
			.constrainAs(ivArrow) {
				top.linkTo(spacerTop.top)
				bottom.linkTo(spacerTop.bottom)
				end.linkTo(parent.end, 16.dp)
			}
			.width(16.dp)
			.height(16.dp)
		)

		Spacer(
			modifier = Modifier.constrainAs(spacerLine){
				top.linkTo(spacerTop.bottom, 25.dp)
				start.linkTo(parent.start)
				end.linkTo(parent.end)
				bottom.linkTo(parent.bottom, 25.dp)
			}.background(color = C_FFF1F1F1)
				.height(45.dp).width(1.dp)
		)

		val barrierBottom = createBottomBarrier(tvCaseNumLab, tvOutstandingAmountLabel)

		Text(
			text = "Case number",
			fontSize = 12.sp,
			color = FF999999,
			modifier = Modifier.constrainAs(tvCaseNumLab) {
				start.linkTo(parent.start, 16.dp)
				top.linkTo(spacerTop.bottom, 16.dp)
				end.linkTo(spacerLine.start, 16.dp)
				width = Dimension.fillToConstraints
			}
		)

		Text(
			text = caseNum,
			fontSize = 18.sp,
			color = C_111111,
			fontWeight = FontWeight.Bold,
			modifier = Modifier.constrainAs(tvCaseNum) {
				start.linkTo(tvCaseNumLab.start)
				top.linkTo(barrierBottom, 8.dp)
				end.linkTo(spacerLine.start, 16.dp)
				bottom.linkTo(parent.bottom, 16.dp)
				width = Dimension.fillToConstraints
			}
		)

		Text(
			text = "Outstanding amount",
			fontSize = 12.sp,
			color = FF999999,
			modifier = Modifier.constrainAs(tvOutstandingAmountLabel) {
				start.linkTo(spacerLine.end, 16.dp)
				top.linkTo(spacerTop.bottom, 16.dp)
				end.linkTo(parent.end, 16.dp)
				width = Dimension.fillToConstraints
			}
		)

		Text(
			text = amount,
			fontSize = 18.sp,
			color = C_111111,
			fontWeight = FontWeight.Bold,
			modifier = Modifier.constrainAs(tvOutstandingAmount) {
				start.linkTo(tvOutstandingAmountLabel.start)
				top.linkTo(barrierBottom, 8.dp)
				end.linkTo(parent.end, 16.dp)
				bottom.linkTo(parent.bottom, 16.dp)
				width = Dimension.fillToConstraints
			}
		)

	}
}

@Preview
@Composable
fun DrawCircle(modifier: Modifier = Modifier, color: Color = Color.Blue,  canvasSize: Dp = 12.dp) {
	Canvas(modifier = modifier.size(canvasSize)) {
		// 定义圆的半径
		val radius = size.minDimension / 2f

		// 定义圆心的位置
		val centerX = size.width / 2f
		val centerY = size.height / 2f

		// 绘制圆
		drawCircle(
			color = color, // 圆的颜色
			radius = radius, // 圆的半径
			center = Offset(centerX, centerY), // 圆心的位置
//			style = Stroke(width = 5f) // 边框宽度
		)
	}
}