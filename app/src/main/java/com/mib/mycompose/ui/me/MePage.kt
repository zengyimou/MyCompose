package com.mib.mycompose.ui.me

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.currentRecomposeScope
import androidx.compose.runtime.snapshots.Snapshot
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.mib.mycompose.ext.toast
import com.mib.mycompose.ui.theme.FF999999
import com.mib.mycompose.ui.widget.MainBackHandler

/**
 *  author : cengyimou
 *  date : 2024/4/11 10:33
 *  description :
 */
@Composable
fun MePage(modifier: Modifier = Modifier, navHostController: NavHostController = rememberNavController()) {
	ConstraintLayout(
		modifier = Modifier
			.fillMaxWidth()
			.fillMaxHeight()
			.background(Color.White),
	) {
		val recompose = currentRecomposeScope
		recompose.invalidate()

		Snapshot.takeSnapshot()
	}
	MainBackHandler()
}