package com.mib.mycompose.ui.widget

import android.annotation.SuppressLint
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.jeremyliao.liveeventbus.LiveEventBus
import com.mib.mycompose.R
import com.mib.mycompose.constants.C
import com.mib.mycompose.constants.C.LINK_TAG
import com.mib.mycompose.constants.Scene
import com.mib.mycompose.constants.Scene.LOGIN_PAGE
import com.mib.mycompose.constants.Scene.START_PAGE
import com.mib.mycompose.event.Event
import com.mib.mycompose.event.LogoutEvent
import com.mib.mycompose.ext.toast
import com.mib.mycompose.manager.UserInfoManager
import com.mib.mycompose.ui.case.CaseDetail
import com.mib.mycompose.ui.case.CasePage
import com.mib.mycompose.ui.contact.ContactPage
import com.mib.mycompose.ui.login.LoginPage
import com.mib.mycompose.ui.login.StartComponent
import com.mib.mycompose.ui.main.MainPage
import com.mib.mycompose.ui.me.MePage
import com.mib.mycompose.ui.theme.C_30B284
import com.mib.mycompose.ui.theme.C_Main
import com.mib.mycompose.ui.theme.FF999999
import com.mib.mycompose.ui.theme.White
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
            popUpTo(route = NavScreen.Login.route) { inclusive = true }
        }
        UserInfoManager.logout()
    }

    SetStatusBarColor(color = C_Main, darkIcons = false)

    Scaffold(
        bottomBar = {
            BottomBar(navController = navController, mainViewModel)
        }
    ) { innerPadding ->
        val colors = MaterialTheme.colors
        val systemUiController = rememberSystemUiController()

        var statusBarColor by remember { mutableStateOf(colors.primaryVariant) }
        var navigationBarColor by remember { mutableStateOf(colors.primaryVariant) }



        Logger.d(LINK_TAG, "=====================Scaffold Content")
        val modifier = Modifier.padding(innerPadding)

        NavHost(navController = navController, startDestination = NavScreen.Start.route) {
            //判断初始跳转
            composable(route = NavScreen.Start.route) {
                StartComponent(navHostController = navController)
            }
            //登陆页面
            composable(route = NavScreen.Login.route) {
                LoginPage(navHostController = navController)
            }
            //4个main tab
            composable(route = NavScreen.TabMain.route) { key ->
                MainPage(modifier = modifier, navHostController = navController)
            }
            composable(route = NavScreen.TabCase.route) {
                CasePage(modifier = modifier, navHostController = navController)
            }
            composable(route = NavScreen.TabContact.route) {
                ContactPage(modifier = modifier, navHostController = navController)
            }
            composable(route = NavScreen.TabMe.route) {
                MePage(modifier = modifier, navHostController = navController)
            }

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

@SuppressLint("RestrictedApi")
@Composable
fun BottomBar(navController: NavController, mainViewModel: MainViewModel) {
    //tab数组
    val tabs = listOf(NavScreen.TabMain, NavScreen.TabCase, NavScreen.TabContact, NavScreen.TabMe)
    Logger.d(C.LINK_TAG, "BottomBar")
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
        ?: NavScreen.TabMain.route
    val routes = remember { tabs.map { it.route } }

    var selectItem by remember { mutableIntStateOf(0) }

    /** 重新登陆需要重置selectItem选中第一个tab*/
    val isResetSelectItem = mainViewModel.resetBottomBarSelectIndexLiveData.observeAsState()
    if (isResetSelectItem.value == true) {
        selectItem = 0
        mainViewModel.resetBottomBarSelectIndex(enable = false)
    }
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
        Logger.d(C.LINK_TAG, "BottomNavigation $currentRoute")
        BottomNavigation(
            backgroundColor = White,
            modifier = Modifier
                .height(64.dp)
                .navigationBarsPadding()
        ) {
            tabs.forEachIndexed { index, item ->
                Logger.d(C.LINK_TAG, "BottomNavigationItem ${item.route}")
                BottomNavigationItem(
                    icon = {
                        val iconRes =
                            if (selectItem == index) selectedIcon[index] else unSelectIcon[index]
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
                            text = tabItemsStr[index],
                            textAlign = TextAlign.Center,
                            fontSize = 10.sp,
                            color = if (selectItem == index) tabTextColor[0] else tabTextColor[1]
                        )
                    },
                    selected = selectItem == index,
                    onClick = {
                        selectItem = index
                        val route = when (selectItem) {
                            0 -> NavScreen.TabMain.route
                            1 -> NavScreen.TabCase.route
                            2 -> NavScreen.TabContact.route
                            3 -> NavScreen.TabMe.route
                            else -> NavScreen.TabMain.route
                        }
                        Logger.d(C.LINK_TAG, "onClick $index")
                        navController.navigate(route) {
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
        BackHandler(enabled = true) {
            context.toast("当前为主页，不能返回")
        }
    }
}

sealed class NavScreen(val route: String) {
    object Start : NavScreen(START_PAGE)


    object Login : NavScreen(LOGIN_PAGE)

    object TabMain : NavScreen(Scene.TAG_MAIN_PAGE)
    object TabCase : NavScreen(Scene.TAG_CASE_PAGE)
    object TabContact : NavScreen(Scene.TAG_CONTACT_PAGE)
    object TabMe : NavScreen(Scene.TAG_ME_PAGE)

    object CaseDetail : NavScreen(Scene.CASE_DETAIL)
}
