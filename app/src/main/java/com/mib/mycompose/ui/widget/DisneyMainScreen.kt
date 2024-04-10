package com.mib.mycompose.ui.widget

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.mib.mycompose.constants.Scene.LOGIN_PAGE
import com.mib.mycompose.constants.Scene.MAIN_PAGE
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

	NavHost(navController = navController, startDestination = LOGIN_PAGE) {
		composable(LOGIN_PAGE){
			LoginContent(nav = navController, listener = mainViewModel.loginListener)
		}

		composable(MAIN_PAGE){
			MainComponent(nav = navController)
		}
	}

}