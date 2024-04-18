package com.mib.mycompose.ui.contact

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.mib.mycompose.ext.toast
import com.mib.mycompose.ui.theme.White
import com.mib.mycompose.ui.widget.MainBackHandler

/**
 *  author : cengyimou
 *  date : 2024/4/11 10:32
 *  description :
 */
@Composable
fun ContactPage(modifier: Modifier = Modifier, navHostController: NavHostController = rememberNavController(),){
	ConstraintLayout(
		modifier = modifier
			.fillMaxWidth()
			.fillMaxHeight()
			.background(White),
	) {

	}
	MainBackHandler()
}