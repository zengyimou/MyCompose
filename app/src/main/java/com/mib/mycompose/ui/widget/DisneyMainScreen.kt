package com.mib.mycompose.ui.widget

import androidx.activity.compose.BackHandler
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.primarySurface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.mib.mycompose.R
import com.mib.mycompose.constants.Scene
import com.mib.mycompose.constants.Scene.LOGIN_PAGE
import com.mib.mycompose.ext.toast
import com.mib.mycompose.manager.UserInfoManager
import com.mib.mycompose.ui.login.LoginComponent
import com.mib.mycompose.ui.main.CasePage
import com.mib.mycompose.ui.main.ContactPage
import com.mib.mycompose.ui.main.MainPage
import com.mib.mycompose.ui.main.MePage
import com.mib.mycompose.ui.main.NavTabScreen
import com.mib.mycompose.ui.theme.C_30B284
import com.mib.mycompose.ui.theme.C_Main
import com.mib.mycompose.ui.theme.FF999999
import com.mib.mycompose.ui.theme.White
import com.mib.mycompose.util.Logger
import com.mib.mycompose.viewmodel.MainViewModel

/**
 *  author : cengyimou
 *  date : 2024/4/9 17:18
 *  description :
 */
@Composable
fun DisneyMainScreen(mainViewModel: MainViewModel) {
	val navController = rememberNavController()

	val colors = MaterialTheme.colors
	val systemUiController = rememberSystemUiController()

	var statusBarColor by remember { mutableStateOf(colors.primaryVariant) }
	var navigationBarColor by remember { mutableStateOf(colors.primaryVariant) }

	val animatedStatusBarColor by animateColorAsState(
		targetValue = statusBarColor,
		animationSpec = tween()
	)
	val animatedNavigationBarColor by animateColorAsState(
		targetValue = navigationBarColor,
		animationSpec = tween()
	)
	//tab数组
	val tabs = listOf(NavScreen.TabMain, NavScreen.TabCase, NavScreen.TabContact, NavScreen.TabMe)

	Scaffold(
		bottomBar = { BottomBar(navController = navController, tabs) }
	){ innerPadding ->
		val modifier = Modifier.padding(innerPadding)
		//初始页面
		val commonPage = if(UserInfoManager.isLogin) NavScreen.TabMain.route else NavScreen.Login.route
		Logger.d("MainComponent","UserInfoManager.isLogin ${UserInfoManager.isLogin}")

		NavHost(navController = navController, startDestination = commonPage) {
			//登陆页面
			composable(route = NavScreen.Login.route){
				LoginComponent(nav = navController)
				LaunchedEffect(Unit) {
					statusBarColor = Color.Transparent
					navigationBarColor = C_Main
				}
			}

//			composable(route = NavScreen.Main.route){
//				MainComponent(nav = navController)
//
//				LaunchedEffect(Unit) {
//					statusBarColor = C_Main
//					navigationBarColor = C_Main
//				}
//			}

			composable(route = NavTabScreen.Main.route) {
				MainPage(modifier = modifier, navHostController = navController)

				LaunchedEffect(Unit) {
					statusBarColor = C_Main
					navigationBarColor = C_Main
				}
			}
			composable(route = NavTabScreen.Case.route) {
				CasePage(modifier = modifier, navHostController = navController)
			}
			composable(route = NavTabScreen.Contact.route) {
				ContactPage(modifier = modifier, navHostController = navController)
			}
			composable(route = NavTabScreen.Me.route) {
				MePage(modifier = modifier, navHostController = navController)
			}
		}
	}

	LaunchedEffect(animatedStatusBarColor, animatedNavigationBarColor) {
		systemUiController.setStatusBarColor(animatedStatusBarColor)
		systemUiController.setNavigationBarColor(animatedNavigationBarColor)
	}
}

@Composable
fun BottomBar(navController: NavController, tabs: List<NavScreen>){
	val navBackStackEntry by navController.currentBackStackEntryAsState()
	val currentRoute = navBackStackEntry?.destination?.route
		?: NavScreen.TabMain.route
	val routes = remember { tabs.map { it.route } }

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

	if (currentRoute in routes) {
		BottomNavigation(
			backgroundColor = White,
			modifier = Modifier.height(64.dp).navigationBarsPadding()
		) {
			tabs.forEachIndexed { index, item ->
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
							text = tabItemsStr[index], textAlign = TextAlign.Center, fontSize = 10.sp,
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
						navController.navigate(route){
							popUpTo(navController.graph.startDestinationId) {
								saveState = true
							}
							launchSingleTop = true
							restoreState = true
						}
					}
				)
			}
		}
	}
}

sealed class NavScreen(val route: String) {

	object Login : NavScreen(LOGIN_PAGE)

	object TabMain : NavScreen(Scene.TAG_MAIN_PAGE)
	object TabCase : NavScreen(Scene.TAG_CASE_PAGE)
	object TabContact : NavScreen(Scene.TAG_CONTACT_PAGE)
	object TabMe : NavScreen(Scene.TAG_ME_PAGE)
}