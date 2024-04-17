package com.mib.mycompose.ui.case

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

/**
 *  author : cengyimou
 *  date : 2024/4/17 11:25
 *  description :
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CaseListPage(modifier: Modifier = Modifier, navHostController: NavHostController = rememberNavController()) {



	Text(
		text = "Page ",
		style = MaterialTheme.typography.h5,
		modifier = Modifier
			.fillMaxSize()
			.wrapContentSize(Alignment.Center),
		textAlign = TextAlign.Center,
	)
}