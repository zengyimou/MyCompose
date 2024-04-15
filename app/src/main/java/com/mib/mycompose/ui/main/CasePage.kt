package com.mib.mycompose.ui.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.constraintlayout.compose.ConstraintLayout
import com.mib.mycompose.ui.theme.FF999999

/**
 *  author : cengyimou
 *  date : 2024/4/11 10:32
 *  description :
 */
@Composable
fun CasePage(modifier: Modifier = Modifier){
	ConstraintLayout(
		modifier = Modifier
			.fillMaxWidth()
			.fillMaxHeight()
			.background(FF999999),
	) {

	}
}