package com.mib.mycompose.ui

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.lifecycle.viewModelScope
import com.jeremyliao.liveeventbus.LiveEventBus
import com.mib.mycompose.base.BaseActivity
import com.mib.mycompose.event.Event
import com.mib.mycompose.event.LogoutEvent
import com.mib.mycompose.ui.widget.DisneyMainScreen
import com.mib.mycompose.ui.widget.NavScreen
import com.mib.mycompose.util.I18nProvider
import com.mib.mycompose.util.I18nState
import com.mib.mycompose.util.ProvideSpacing
import com.mib.mycompose.viewmodel.BaseViewModel
import com.mib.mycompose.viewmodel.MainViewModel
import kotlinx.coroutines.launch

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

	private fun initDefaultLanguage() {
		mainViewModel.viewModelScope.launch {
			val tag = I18nState.getCurrentLanguage(this@LoginActivity)
			I18nState.setTag(tag = tag)
		}
	}

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		initDefaultLanguage()
		setContent {
			ProvideSpacing{
				I18nProvider(I18nState.currentTag.value) {
					DisneyMainScreen(this, mainViewModel)
				}
			}
		}
	}


}

