package com.mib.mycompose.ui.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.mib.mycompose.ui.theme.FF999999

/**
 *  author : cengyimou
 *  date : 2024/4/11 10:33
 *  description :
 */
@Composable
fun MePage(modifier: Modifier = Modifier, navHostController: NavHostController = rememberNavController(),){
	ConstraintLayout(
		modifier = Modifier
			.fillMaxWidth()
			.fillMaxHeight()
			.background(FF999999),
	) {

	}
}