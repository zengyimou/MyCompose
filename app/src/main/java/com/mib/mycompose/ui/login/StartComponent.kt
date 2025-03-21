package com.mib.mycompose.ui.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.mib.mycompose.R
import com.mib.mycompose.manager.UserInfoManager
import com.mib.mycompose.ui.widget.NavScreen
import com.mib.mycompose.util.Logger
import com.mib.mycompose.util.RouteUtils.navigateStart

/**
 *  author : yimou
 *  date : 2024/4/27 16:12
 *  description :
 */
@Preview
@Composable
fun StartComponent(navHostController: NavHostController = rememberNavController()) {
	LaunchedEffect(Unit) {
		if(UserInfoManager.isLogin){
			Logger.d("zym", "nav NavScreen.TabMain.route")
			navHostController.navigateStart(routeName = NavScreen.TabMain.route)
		}else{
			Logger.d("zym", "nav NavScreen.Login.route")
			navHostController.navigateStart(routeName = NavScreen.Login.route)
		}
	}

	Box(modifier = Modifier.fillMaxSize()) {
		Image(
			painter = painterResource(id = R.mipmap.bg_splash),
			contentDescription = null,
			modifier = Modifier
				.fillMaxWidth()
				.fillMaxHeight(),
			contentScale = ContentScale.Crop
		)

		Image(
			painter = painterResource(id = R.drawable.icon_welcome),
			contentDescription = null,
			modifier = Modifier
				.align(Alignment.TopCenter)
				.fillMaxWidth(fraction = 1f)
				.padding(horizontal = 50.dp, vertical = 200.dp)
				.wrapContentHeight(),
			contentScale = ContentScale.Inside
		)
	}
}