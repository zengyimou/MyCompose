package com.mib.mycompose.ui.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.mib.mycompose.R
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
		Logger.d("zym", "nav NavScreen.TabMain.route")
		navHostController.navigateStart(routeName = NavScreen.TabMain.route)
	}else{
		Logger.d("zym", "nav NavScreen.Login.route")
		navHostController.navigateStart(routeName = NavScreen.Login.route)
	}

	Surface(modifier = Modifier.fillMaxSize().padding(16.dp)) {
		Image(
			painter = painterResource(id = R.drawable.icon_welcome),
			contentDescription = null,
			modifier = Modifier.padding(16.dp),
			contentScale = ContentScale.Fit
		)
	}
}