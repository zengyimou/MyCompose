package com.mib.mycompose.ui.widget

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.absolutePadding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavHostController
import com.mib.mycompose.R
import com.mib.mycompose.ui.theme.White

/**
 *  author : cengyimou
 *  date : 2024/4/10 10:30
 *  description :
 */
@Composable
fun MainComponent(nav: NavHostController? = null) {

	var selectItem by remember { mutableIntStateOf(0) }
	val context = LocalContext.current
	val tabItemsStr = listOf(
		context.getString(R.string.tab_main),
		context.getString(R.string.tab_case),
		context.getString(R.string.tab_contact),
		context.getString(R.string.tab_me)
	)

	Surface{
		Scaffold(
			content = null,
			bottomBar = {
				BottomNavigation {
					tabItemsStr.forEachIndexed { index, item ->
						BottomNavigationItem(
							icon = {
								var iconRes = R.drawable.ic_ta
								Icon(
									painter = painterResource(id = iconRes),
									contentDescription = null,
									modifier = Modifier
										.size(24.dp)
										.padding(bottom = 4.dp),
								)},
							label = { Text(item) },
							selected = selectItem == index,
							onClick = { selectItem = index }
						)
					}
				}
			}
		)
	}

}

@Composable
fun MainPage(){
	ConstraintLayout(
		modifier = Modifier
			.absolutePadding(top = 40.dp, left = 16.dp, right = 16.dp)
			.fillMaxWidth()
			.height(IntrinsicSize.Min)
			.background(White),
	) {

	}
}

@Composable
fun CasePage(){
	ConstraintLayout(
		modifier = Modifier
			.absolutePadding(top = 40.dp, left = 16.dp, right = 16.dp)
			.fillMaxWidth()
			.height(IntrinsicSize.Min)
			.background(White),
	) {

	}
}

@Composable
fun ContactPage(){
	ConstraintLayout(
		modifier = Modifier
			.absolutePadding(top = 40.dp, left = 16.dp, right = 16.dp)
			.fillMaxWidth()
			.height(IntrinsicSize.Min)
			.background(White),
	) {

	}
}

@Composable
fun MePage(){
	ConstraintLayout(
		modifier = Modifier
			.absolutePadding(top = 40.dp, left = 16.dp, right = 16.dp)
			.fillMaxWidth()
			.height(IntrinsicSize.Min)
			.background(White),
	) {

	}
}

