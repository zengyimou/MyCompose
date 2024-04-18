package com.mib.mycompose.ui.widget

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.mib.mycompose.R
import com.mib.mycompose.ui.theme.C_FF000000
import com.mib.mycompose.ui.theme.C_Main
import com.mib.mycompose.ui.theme.White

/**
 *  author : cengyimou
 *  date : 2024/4/18 11:40
 *  description :
 */
@Preview
@Composable
fun DropDownTab(
	modifier: Modifier = Modifier,
	tabText: String = "tabasd",
	isSelected: Boolean = false,
	onClick: ()-> Unit = {},
) {
	ConstraintLayout(
		modifier = modifier
			.heightIn(44.dp, 64.dp)
			.background(White).clickable {
				onClick.invoke()
			},
	) {
		val (tvTab, ivArrow) = createRefs()
		/** 旋转状态值*/
		val rotationValue by animateFloatAsState(targetValue = if (isSelected) -180f else 0f, label = "")

		Text(
			text = tabText,
			fontSize = 14.sp,
			color = if(isSelected) C_Main else C_FF000000,
			modifier = Modifier.constrainAs(tvTab) {
				start.linkTo(parent.start, 12.dp)
				top.linkTo(parent.top)
				end.linkTo(ivArrow.start, 4.dp)
				bottom.linkTo(parent.bottom)
				width = Dimension.fillToConstraints
			},
		)

		Image(
			painter = painterResource(id = R.drawable.ic_drop_down),
			contentDescription = null,
			modifier = Modifier
				.constrainAs(ivArrow) {
					top.linkTo(parent.top)
					bottom.linkTo(parent.bottom)
					end.linkTo(parent.end, 12.dp)
				}
				.width(11.dp)
				.height(11.dp)
				.graphicsLayer {
					rotationX = rotationValue
				},
		)
	}

}