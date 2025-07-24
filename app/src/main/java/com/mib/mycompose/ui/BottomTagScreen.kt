package com.mib.mycompose.ui

import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mib.mycompose.constants.C.LINK_TAG
import com.mib.mycompose.ui.case.CasePage
import com.mib.mycompose.ui.contact.ContactPage
import com.mib.mycompose.ui.main.MainPage
import com.mib.mycompose.ui.main.MainPageViewModel
import com.mib.mycompose.ui.me.MePage
import com.mib.mycompose.ui.widget.BottomBar
import com.mib.mycompose.ui.widget.NavScreen
import com.mib.mycompose.util.Logger
import com.mib.mycompose.viewmodel.MainViewModel

/**
 *  author : cengyimou
 *  date : 2025/5/22 11:19
 *  description :
 */
@Preview
@Composable
fun BottomTagScreen(
    modifier: Modifier = Modifier,
    navMainHostController: NavHostController = rememberNavController(),
    mainPageViewModel: MainPageViewModel = viewModel(),
    mainViewModel: MainViewModel = viewModel()
) {
    val navBottomTabController = rememberNavController()

    Scaffold(
        bottomBar = {
            BottomBar(navController = navBottomTabController, mainViewModel)
        }
    ) { innerPadding ->

        Logger.d(LINK_TAG, "=====================Scaffold Content")
        val modifier = Modifier.padding(innerPadding)
        NavHost(
            navController = navBottomTabController,
            startDestination = NavScreen.TabMain.route,
            enterTransition = {
                slideInHorizontally(
                    initialOffsetX = { fullWidth -> fullWidth },
                    animationSpec = tween(300)
                )
            },
            exitTransition = {
                slideOutHorizontally(
                    targetOffsetX = { fullWidth -> -fullWidth },
                    animationSpec = tween(300)
                )
            },
        ) {

            //4个main tab
            composable(route = NavScreen.TabMain.route) { key ->
                MainPage(modifier = modifier, navHostController = navMainHostController)
            }
            composable(route = NavScreen.TabCase.route) {
                CasePage(modifier = modifier, navHostController = navMainHostController)
            }
            composable(route = NavScreen.TabContact.route) {
                ContactPage(modifier = modifier, navHostController = navMainHostController)
            }
            composable(route = NavScreen.TabMe.route) {
                MePage(modifier = modifier, navHostController = navMainHostController)
            }

        }
    }
}