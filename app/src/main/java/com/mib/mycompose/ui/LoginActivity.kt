package com.mib.mycompose.ui

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.jeremyliao.liveeventbus.LiveEventBus
import com.mib.mycompose.base.BaseActivity
import com.mib.mycompose.event.Event
import com.mib.mycompose.event.LogoutEvent
import com.mib.mycompose.ui.widget.DisneyMainScreen
import com.mib.mycompose.ui.widget.NavScreen
import com.mib.mycompose.viewmodel.BaseViewModel
import com.mib.mycompose.viewmodel.MainViewModel

/**
 *  author : cengyimou
 *  date : 2023/11/30 18:00
 *  description :
 */
class LoginActivity : BaseActivity() {

	val mainViewModel: MainViewModel by viewModels()

	override fun createViewModel(): BaseViewModel {
		return mainViewModel
	}

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContent {
			DisneyMainScreen(this, mainViewModel)
		}
	}


}

