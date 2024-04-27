package com.mib.mycompose.ui.login

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.mib.mycompose.constants.C
import com.mib.mycompose.manager.UserInfoManager
import com.mib.mycompose.ui.widget.NavScreen
import com.mib.mycompose.util.Logger
import com.mib.mycompose.util.RouteUtils.navigateStart

/**
 *  author : yimou
 *  date : 2024/4/27 16:12
 *  description :
 */
@Composable
fun StartComponent(navHostController: NavHostController) {
	if(UserInfoManager.isLogin){
		navHostController.navigateStart(routeName = NavScreen.TabMain.route)
	}else{
		navHostController.navigateStart(routeName = NavScreen.Login.route)
	}
}