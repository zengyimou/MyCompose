package com.mib.mycompose.ui.widget

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mib.mycompose.R
import com.mib.mycompose.ui.theme.C_222222

/**
 *  author : cengyimou
 *  date : 2024/4/22 16:45
 *  description :
 */
@Preview
@Composable
fun SettingItem(
	modifier: Modifier = Modifier,
	res: Int = R.mipmap.icon_calllog,
	title: String = "Sms",
	itemClickListener: () -> Unit = {}
) {
	Row(
		modifier = modifier
			.background(color = Color.White)
			.fillMaxWidth()
			.clickable { itemClickListener.invoke() },
		verticalAlignment = Alignment.CenterVertically,
	) {
		Image(
			painter = painterResource(id = res),
			contentDescription = null,
			modifier = Modifier
				.height(28.dp)
				.width(28.dp),
		)
		Text(
			text = title, fontSize = 14.sp, color = C_222222,
			modifier = Modifier
				.weight(weight = 1f)
				.padding(horizontal = 12.dp, vertical = 10.dp),
			maxLines = 1,
			softWrap = false,
			overflow = TextOverflow.Ellipsis,
		)

		Image(
			painter = painterResource(id = R.drawable.icon_arrow_right),
			contentDescription = null,
			modifier = Modifier
				.height(10.dp)
				.width(10.dp),
		)
	}
}