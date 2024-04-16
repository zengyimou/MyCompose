package com.mib.mycompose.ui.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.mib.mycompose.ui.theme.White

/**
 *  author : cengyimou
 *  date : 2024/4/11 10:32
 *  description :
 */
@Composable
fun ContactPage(modifier: Modifier = Modifier, navHostController: NavHostController = rememberNavController(),){
	ConstraintLayout(
		modifier = Modifier
			.fillMaxWidth()
			.fillMaxHeight()
			.background(White),
	) {

	}
}