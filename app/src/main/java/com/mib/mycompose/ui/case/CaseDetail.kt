package com.mib.mycompose.ui.case

import androidx.activity.ComponentActivity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.mib.mycompose.R
import com.mib.mycompose.ui.widget.BackTitleLayout
import com.mib.mycompose.util.Logger

/**
 *  author : cengyimou
 *  date : 2025/1/8 15:24
 *  description :
 */
@Preview
@Composable
fun CaseDetail(
    businessId: String = "",
    navHostController: NavHostController = rememberNavController()
) {
    val caseDetailViewModel: CaseViewModel = viewModel(LocalContext.current as ComponentActivity)
    LaunchedEffect(Unit){
        Logger.d("zym", "${caseDetailViewModel.hashCode()} CaseDetail LaunchedEffect!!! ")
        Logger.d("zym", "businessId ${businessId}")
        Logger.d("zym", "CaseDetail test ${caseDetailViewModel.test}")
    }

    BackTitleLayout(title = stringResource(R.string.task_title)){

    }
}