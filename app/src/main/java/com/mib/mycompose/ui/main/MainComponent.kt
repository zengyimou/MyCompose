package com.mib.mycompose.ui.main

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Scaffold
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
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mib.mycompose.R
import com.mib.mycompose.constants.Scene
import com.mib.mycompose.ext.toast
import com.mib.mycompose.ui.case.CasePage
import com.mib.mycompose.ui.contact.ContactPage
import com.mib.mycompose.ui.me.MePage
import com.mib.mycompose.ui.theme.C_30B284
import com.mib.mycompose.ui.theme.FF999999
import com.mib.mycompose.ui.theme.White
import com.mib.mycompose.util.Logger

/**
 *  author : cengyimou
 *  date : 2024/4/10 10:30
 *  description :
 */
@Preview
@Composable
fun MainComponent(nav: NavHostController = rememberNavController(), mainPageViewModel: MainPageViewModel = viewModel()) {
	Logger.d("MainComponent", "current:${LocalViewModelStoreOwner.current}")
	var selectItem by remember { mutableIntStateOf(0) }
	val context = LocalContext.current
	val tabItemsStr = listOf(
		context.getString(R.string.tab_main),
		context.getString(R.string.tab_case),
		context.getString(R.string.tab_contact),
		context.getString(R.string.tab_me)
	)

	val selectedIcon = listOf(
		R.mipmap.icon_tab_btn_main_selected,
		R.mipmap.icon_tab_btn_case_selected,
		R.mipmap.icon_tab_btn_contact_selected,
		R.mipmap.icon_tab_btn_me_selected
	)

	val unSelectIcon = listOf(
		R.mipmap.icon_tab_btn_main_unselected,
		R.mipmap.icon_tab_btn_case_unselected,
		R.mipmap.icon_tab_btn_contact_unselected,
		R.mipmap.icon_tab_btn_me_unselected
	)

	val tabTextColor = listOf(
		C_30B284,
		FF999999,
	)

	val bottomNavController = rememberNavController()

	Box {
		Scaffold(
			modifier = Modifier
				.fillMaxWidth()
				.fillMaxHeight()
				.background(color = White),
			bottomBar = {
				BottomNavigation(
					backgroundColor = White,
					modifier = Modifier
						.height(64.dp)
						.navigationBarsPadding()
				) {
					tabItemsStr.forEachIndexed { index, item ->
						BottomNavigationItem(
							icon = {
								val iconRes = if (selectItem == index) selectedIcon[index] else unSelectIcon[index]
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
							label = {
								Text(
									text = item, textAlign = TextAlign.Center, fontSize = 10.sp,
									color = if (selectItem == index) tabTextColor[0] else tabTextColor[1]
								)
							},
							selected = selectItem == index,
							onClick = {
								selectItem = index
								val route = when (selectItem) {
									0 -> NavTabScreen.Main.route
									1 -> NavTabScreen.Case.route
									2 -> NavTabScreen.Contact.route
									3 -> NavTabScreen.Me.route
									else -> NavTabScreen.Main.route
								}
								bottomNavController.navigate(route)
							}
						)
					}
				}
			}
		) { innerPadding ->
			val modifier = Modifier.padding(innerPadding)
//			Crossfade(selectItem, label = "") { destination ->
//				when(destination) {
//					0 -> MainPage(modifier = modifier)
//					1 -> CasePage(modifier = modifier)
//					2 -> ContactPage(modifier = modifier)
//					3 -> MePage(modifier = modifier)
//					else -> MainPage(modifier = modifier)
//				}
//			}
			NavHost(navController = bottomNavController, startDestination = NavTabScreen.Main.route) {
				composable(route = NavTabScreen.Main.route) {
					MainPage(modifier = modifier, navHostController = nav)
				}
				composable(route = NavTabScreen.Case.route) {
					CasePage(modifier = modifier, navHostController = nav)
				}
				composable(route = NavTabScreen.Contact.route) {
					ContactPage(modifier = modifier, navHostController = nav)
				}
				composable(route = NavTabScreen.Me.route) {
					MePage(modifier = modifier, navHostController = nav)
				}
			}

		}
	}

	BackHandler(enabled = true) {
		context.toast("当前为主页，不能返回")
	}

}

sealed class NavTabScreen(val route: String) {
	object Main : NavTabScreen(Scene.TAG_MAIN_PAGE)
	object Case : NavTabScreen(Scene.TAG_CASE_PAGE)
	object Contact : NavTabScreen(Scene.TAG_CONTACT_PAGE)
	object Me : NavTabScreen(Scene.TAG_ME_PAGE)
}