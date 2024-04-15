package com.mib.mycompose.ui.widget

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.mib.mycompose.constants.Scene.LOGIN_PAGE
import com.mib.mycompose.constants.Scene.MAIN_PAGE
import com.mib.mycompose.manager.UserInfoManager
import com.mib.mycompose.ui.login.LoginComponent
import com.mib.mycompose.ui.main.MainComponent
import com.mib.mycompose.ui.theme.C_Main
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

	//初始页面
	val commonPage = if(UserInfoManager.isLogin) NavScreen.Main.route else NavScreen.Login.route

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
		//主页
		composable(route = NavScreen.Main.route){
			MainComponent(nav = navController)

			LaunchedEffect(Unit) {
				statusBarColor = C_Main
				navigationBarColor = C_Main
			}
		}
	}
	LaunchedEffect(animatedStatusBarColor, animatedNavigationBarColor) {
		systemUiController.setStatusBarColor(animatedStatusBarColor)
		systemUiController.setNavigationBarColor(animatedNavigationBarColor)
	}
}

sealed class NavScreen(val route: String) {

	object Login : NavScreen(LOGIN_PAGE)
	object Main : NavScreen(MAIN_PAGE) {

//		const val routeWithArgument: String = "PosterDetails/{posterId}"
//		const val argument0: String = "posterId"
	}
}