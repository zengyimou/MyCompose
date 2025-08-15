package com.mib.mycompose.ui.widget

import android.annotation.SuppressLint
import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import com.mib.mycompose.R
import com.mib.mycompose.constants.C
import com.mib.mycompose.ext.toast
import com.mib.mycompose.ui.theme.C_30B284
import com.mib.mycompose.ui.theme.FF999999
import com.mib.mycompose.ui.theme.White
import com.mib.mycompose.util.Logger
import com.mib.mycompose.viewmodel.MainViewModel

/**
 *  author : cengyimou
 *  date : 2025/5/22 11:40
 *  description :
 */
@SuppressLint("RestrictedApi")
@Composable
fun BottomBar(navController: NavController, mainViewModel: MainViewModel) {
    //tab数组
    val tabs = listOf(NavScreen.TabMain, NavScreen.TabCase, NavScreen.TabContact, NavScreen.TabMe)
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val activity = LocalContext.current as Activity?

    Logger.d(C.LINK_TAG, "BottomBar currentRoute ${currentRoute}")
    val routes = remember { tabs.map { it.route } }

    var selectItem by rememberSaveable { mutableIntStateOf(0) }

    var lastBackPressTime by remember { mutableStateOf(0L) }

    /** 重新登陆需要重置selectItem选中第一个tab*/
//    val isResetSelectItem = mainViewModel.resetBottomBarSelectIndexLiveData.observeAsState()
//    if (isResetSelectItem.value == true) {
//        selectItem = 0
//        mainViewModel.resetBottomBarSelectIndex(enable = false)
//    }
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
        Logger.d("zym", "BottomNavigation $currentRoute")
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
                            else -> NavScreen.TabMe.route
                        }
                        Logger.d(C.LINK_TAG, "onClick $index $route")
                        navController.navigate(route) {
                            val id = navController.graph.findStartDestination().id
                            Logger.d(C.LINK_TAG, "id $id")
                            popUpTo(id) {
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
            val currentTime = System.currentTimeMillis()
            if (currentTime - lastBackPressTime < 2000) {
                activity?.finish()
            } else {
                lastBackPressTime = currentTime
                context.toast("再按一次退出")
            }

        }
    }
}