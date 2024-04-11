package com.mib.mycompose.ui.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mib.mycompose.ui.theme.White
import com.mib.mycompose.util.Logger
import com.mib.mycompose.viewmodel.MainViewModel

/**
 *  author : cengyimou
 *  date : 2024/4/11 10:31
 *  description :
 */
@Composable
fun MainPage(mainPageViewModel: MainPageViewModel= viewModel()){
	Logger.d("MainComponent","MainPage ${mainPageViewModel.hashCode()}")
	ConstraintLayout(
		modifier = Modifier
			.fillMaxWidth()
			.fillMaxHeight()
			.background(White),
	) {
		val (
			tvAccount,
		) = createRefs()



	}
}