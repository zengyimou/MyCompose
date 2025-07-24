package com.mib.mycompose.ui.widget

import androidx.activity.ComponentActivity
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.jeremyliao.liveeventbus.LiveEventBus
import com.mib.mycompose.constants.C
import com.mib.mycompose.constants.C.LINK_TAG
import com.mib.mycompose.constants.Scene
import com.mib.mycompose.constants.Scene.LOGIN_PAGE
import com.mib.mycompose.constants.Scene.MAIN_TAB_PAGE
import com.mib.mycompose.constants.Scene.START_PAGE
import com.mib.mycompose.event.Event
import com.mib.mycompose.event.LogoutEvent
import com.mib.mycompose.manager.UserInfoManager
import com.mib.mycompose.ui.BottomTagScreen
import com.mib.mycompose.ui.case.CaseDetail
import com.mib.mycompose.ui.login.LoginPage
import com.mib.mycompose.ui.login.StartComponent
import com.mib.mycompose.ui.theme.C_Main
import com.mib.mycompose.util.Logger
import com.mib.mycompose.viewmodel.MainViewModel
import com.mib.mycompose.viewmodel.NavNavControllerViewModel

/**
 *  author : cengyimou
 *  date : 2024/4/9 17:18
 *  description :
 */

@Composable
fun DisneyMainScreen(lifecycleOwner: LifecycleOwner, mainViewModel: MainViewModel) {
    Logger.d(C.LINK_TAG, "DisneyMainScreen")
    val navViewModel: NavNavControllerViewModel = viewModel(LocalContext.current as ComponentActivity)
    val navController = rememberNavController()
    navViewModel.navController = navController
    //监听登陆过期事件
    LiveEventBus.get<LogoutEvent>(Event.EVENT_LOGOUT).observe(lifecycleOwner) {
        mainViewModel.resetBottomBarSelectIndex(true)
        //回退到登陆页面，并推出其他页面
        navController.navigate(route = NavScreen.Login.route) {
            popUpTo(NavScreen.Login.route) { inclusive = true } // 清空整个导航堆栈
            launchSingleTop = true
        }

        UserInfoManager.logout()
    }

    navController.addOnDestinationChangedListener { controller, destination, arguments ->
        Logger.d("NavStack", "Navigating to ${destination.route}")
    }

//    SetStatusBarColor(color = C_Main, darkIcons = false)
    SetEdgeToEdge(color = C_Main, darkIcons = true)

    Scaffold(
//        bottomBar = {
//            BottomBar(navController = navController, mainViewModel)
//        }
    ) { innerPadding ->
        val colors = MaterialTheme.colors
//        val systemUiController = rememberSystemUiController()
//        var statusBarColor by remember { mutableStateOf(colors.primaryVariant) }
//        var navigationBarColor by remember { mutableStateOf(colors.primaryVariant) }



        Logger.d(LINK_TAG, "=====================Scaffold Content")
        val modifier = Modifier.padding(innerPadding)
        NavHost(
            navController = navController,
            startDestination = NavScreen.Start.route,
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
            //判断初始跳转
            composable(route = NavScreen.Start.route) {
                StartComponent(navHostController = navController)
            }
            //登陆页面
            composable(route = NavScreen.Login.route) {
                LoginPage(navHostController = navController)
            }
            composable(route = NavScreen.BottomTab.route) {
                BottomTagScreen(navMainHostController = navController, mainViewModel = mainViewModel)
            }

//            //4个main tab
//            composable(route = NavScreen.TabMain.route) { key ->
//                MainPage(modifier = modifier, navHostController = navController)
//            }
//            composable(route = NavScreen.TabCase.route) {
//                CasePage(modifier = modifier, navHostController = navController)
//            }
//            composable(route = NavScreen.TabContact.route) {
//                ContactPage(modifier = modifier, navHostController = navController)
//            }
//            composable(route = NavScreen.TabMe.route) {
//                MePage(modifier = modifier, navHostController = navController)
//            }

            //案件详情
            composable(
                route = "${NavScreen.CaseDetail.route}/{businessId}",
                arguments = listOf(
                    navArgument("businessId") { type = NavType.StringType },
                )
            ) { backStackEntry ->
                val businessId = backStackEntry.arguments?.getString("businessId") ?: ""
                Logger.d("zym", "businessId $businessId")
                CaseDetail(businessId = businessId, navHostController = navController)
            }
        }
    }

}

sealed class NavScreen(val route: String) {
    object Start : NavScreen(START_PAGE)


    object Login : NavScreen(LOGIN_PAGE)
    object BottomTab: NavScreen(MAIN_TAB_PAGE)

    object TabMain : NavScreen(Scene.TAG_MAIN_PAGE)
    object TabCase : NavScreen(Scene.TAG_CASE_PAGE)
    object TabContact : NavScreen(Scene.TAG_CONTACT_PAGE)
    object TabMe : NavScreen(Scene.TAG_ME_PAGE)

    object CaseDetail : NavScreen(Scene.CASE_DETAIL)
}
