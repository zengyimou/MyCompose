package com.mib.mycompose.ui.main

import androidx.activity.compose.BackHandler
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.mib.mycompose.R
import com.mib.mycompose.ext.toast
import com.mib.mycompose.ui.theme.FF999999
import com.mib.mycompose.ui.theme.White
import com.mib.mycompose.util.Logger
import com.mib.mycompose.viewmodel.MainViewModel

/**
 *  author : cengyimou
 *  date : 2024/4/10 10:30
 *  description :
 */
@Preview
@Composable
fun MainComponent(nav: NavHostController? = null, mainPageViewModel: MainPageViewModel = viewModel()){
	Logger.d("MainComponent", "current:${LocalViewModelStoreOwner.current}")
	var selectItem by remember { mutableIntStateOf(0) }
	val context = LocalContext.current
	Logger.d("MainComponent","MainComponent ${mainPageViewModel.hashCode()}")
	Logger.d("MainComponent","LocalContext.current ${LocalContext.current}")
	val tabItemsStr = listOf(
		context.getString(R.string.tab_main),
		context.getString(R.string.tab_case),
		context.getString(R.string.tab_contact),
		context.getString(R.string.tab_me)
	)

	Surface(
		modifier = Modifier.background(color = White),
	){
		Scaffold(
			bottomBar = {
				BottomNavigation(
					backgroundColor = White,
					modifier = Modifier.height(64.dp)
				) {
					tabItemsStr.forEachIndexed { index, item ->
						BottomNavigationItem(
							icon = {
								var iconRes = R.mipmap.icon_tab_btn_case_selected
								Box(
									contentAlignment = Alignment.Center
								) {
									Image(
										painter = painterResource(id = iconRes),
										contentDescription = null,
										contentScale = ContentScale.Crop
									)
								}
							},
							label = { Text(text = item, textAlign = TextAlign.Center) },
							selected = selectItem == index,
							onClick = { selectItem = index }
						)
					}
				}
			}
		){ _ ->
			Crossfade(selectItem, label = "") { destination ->
				when(destination) {
					0 -> MainPage()
					1 -> CasePage()
					2 -> ContactPage()
					3 -> MePage()
					else -> MainPage()
				}
			}

		}
	}

	BackHandler(enabled = true) {
		context.toast("当前为主页，不能返回")
	}

}