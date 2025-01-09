package com.mib.mycompose.ui.widget

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.mib.mycompose.R
import com.mib.mycompose.model.NewCaseListItem
import com.mib.mycompose.ui.theme.C_111111
import com.mib.mycompose.ui.theme.C_999999
import com.mib.mycompose.ui.theme.C_E7FFE6
import com.mib.mycompose.ui.theme.C_F6F6F6
import com.mib.mycompose.ui.theme.C_FF000000
import com.mib.mycompose.ui.theme.C_FF9900
import com.mib.mycompose.ui.theme.C_FFEBCD
import com.mib.mycompose.ui.theme.C_Main
import com.mib.mycompose.ui.theme.White
import com.mib.mycompose.util.AmoutUtil
import com.mib.mycompose.util.DateFormatConverter
import com.mib.mycompose.util.DateFormatConverter.DATE_FORMAT8
import java.util.Date

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
    onClick: () -> Unit = {},
) {
    ConstraintLayout(
        modifier = modifier
			.heightIn(44.dp, 64.dp)
			.background(White)
			.clickable {
				onClick.invoke()
			},
    ) {
        val (tvTab, ivArrow) = createRefs()
        /** 旋转状态值*/
        val rotationValue by animateFloatAsState(
            targetValue = if (isSelected) -180f else 0f,
            label = ""
        )

        Text(
            text = tabText,
            fontSize = 14.sp,
            color = if (isSelected) C_Main else C_FF000000,
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

/**
 *
 * @param modifier Modifier
 * @param newCaseListItem NewCaseListItem?
 * @param onClick Function0<Unit>
 */
@OptIn(ExperimentalLayoutApi::class)
@Preview
@Composable
fun TaskListItem(
    modifier: Modifier = Modifier,
    item: NewCaseListItem? = null,
    onClick: () -> Unit = {},
) {

    ConstraintLayout(
        modifier = modifier
			.wrapContentHeight()
			.fillMaxWidth()
			.background(White)
			.padding(bottom = 10.dp)
			.clickable {
				onClick.invoke()
			},
    ) {
        val (tvCustomerName, tvCallBackTime, tvDueAmount, tvDueAmountLab,
            vLineTop, vLineBottom, tvRepaymentAmount, tvRepaymentAmountLab, flowLayout) = createRefs()

        Text(
            modifier = Modifier.constrainAs(tvCustomerName) {
                top.linkTo(parent.top, 22.dp)
                start.linkTo(parent.start, 16.dp)
                end.linkTo(parent.end, 16.dp)
                width = Dimension.fillToConstraints
            },
            text = item?.customerName ?: "null",
            color = C_111111,
            fontSize = 14.sp,
        )

        val reminderTimeText = if (item != null && item.reminderTime != 0) {
            DateFormatConverter.getCustomString(
                Date(item.reminderTime.toLong().times(1000)),
                DATE_FORMAT8
            )
        } else {
            DateFormatConverter.getCustomString(Date(), DATE_FORMAT8)
        }
        TextWithStartIcon(
            modifier = Modifier
				.constrainAs(tvCallBackTime) {
					top.linkTo(parent.top)
					end.linkTo(parent.end)
					width = Dimension.wrapContent
				}
				.background(color = C_FFEBCD, shape = RoundedCornerShape(bottomStart = 12.dp))
				.padding(horizontal = 8.dp, vertical = 2.dp),
            text = reminderTimeText,
            color = C_FF9900,
            fontSize = 14.sp,
            res = R.mipmap.ic_callback_orange_small
        )

        Spacer(
            modifier = Modifier
				.constrainAs(vLineTop) {
					top.linkTo(tvCustomerName.bottom, 8.dp)
					end.linkTo(parent.end, 16.dp)
					start.linkTo(parent.start, 16.dp)
					width = Dimension.fillToConstraints
				}
				.background(color = C_F6F6F6)
				.height(1.dp)
        )


        FlowRow(modifier = Modifier
			.padding(horizontal = 16.dp)
			.constrainAs(flowLayout) {
				top.linkTo(vLineTop.bottom, 8.dp)
				end.linkTo(parent.end)
				start.linkTo(parent.start)
				width = Dimension.fillToConstraints
			}) {
            FlowTagItem(modifier = Modifier.padding(end = 4.dp), text = "text1")
            FlowTagItem(modifier = Modifier.padding(end = 4.dp), text = "text1")
            FlowTagItem(modifier = Modifier.padding(end = 4.dp), text = "text1")
            FlowTagItem(modifier = Modifier.padding(end = 4.dp), text = "text1")
            FlowTagItem(modifier = Modifier.padding(end = 4.dp), text = "text1")
        }

        Text(
            modifier = Modifier.constrainAs(tvDueAmountLab) {
                top.linkTo(flowLayout.bottom, 8.dp)
                start.linkTo(parent.start, 16.dp)
                end.linkTo(parent.end, 16.dp)
                width = Dimension.fillToConstraints
            },
            text = "Due Amount",
            color = C_999999,
            fontSize = 10.sp,
        )

        Text(
            modifier = Modifier.constrainAs(tvDueAmount) {
                top.linkTo(tvDueAmountLab.bottom, 2.dp)
                start.linkTo(parent.start, 16.dp)
                end.linkTo(parent.end, 16.dp)
                width = Dimension.fillToConstraints
            },
            text = AmoutUtil.formatFloatNumber(item?.remainAmount ?: "0"),
            color = C_111111,
            fontSize = 18.sp,
        )

        Spacer(
            modifier = Modifier
				.constrainAs(vLineBottom) {
					top.linkTo(tvDueAmount.bottom, 16.dp)
					end.linkTo(parent.end, 16.dp)
					start.linkTo(parent.start, 16.dp)
					width = Dimension.fillToConstraints
				}
				.background(color = C_F6F6F6)
				.height(1.dp)
        )

        Text(
            modifier = Modifier.constrainAs(tvRepaymentAmountLab) {
                top.linkTo(vLineBottom.bottom, 12.dp)
                start.linkTo(parent.start, 16.dp)
                end.linkTo(tvRepaymentAmount.start, 10.dp)
                width = Dimension.fillToConstraints
            },
            text = "Performance repayment amount:",
            color = C_999999,
            fontSize = 12.sp,
        )

        Text(
            modifier = Modifier.constrainAs(tvRepaymentAmount) {
                top.linkTo(tvRepaymentAmountLab.top)
                start.linkTo(tvRepaymentAmountLab.end)
                end.linkTo(parent.end, 16.dp)
                width = Dimension.wrapContent
            },
            textAlign = TextAlign.End,
            text = item?.performanceAmount ?: "123123",
            color = C_111111,
            fontSize = 18.sp,
        )

    }
}

@Preview
@Composable
fun FlowTagItem(
    modifier: Modifier = Modifier,
    text: String = "tag"
) {
    Text(
        modifier = modifier
			.background(color = C_E7FFE6, shape = RoundedCornerShape(2.dp))
			.padding(horizontal = 5.dp, vertical = 2.dp),
        text = text,
        color = C_Main,
        fontSize = 10.sp,
    )
}