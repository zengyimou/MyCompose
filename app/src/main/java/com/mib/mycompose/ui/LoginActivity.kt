package com.mib.mycompose.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.absolutePadding
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.mib.mycompose.R
import com.mib.mycompose.base.BaseActivity
import com.mib.mycompose.ext.toast
import com.mib.mycompose.ui.theme.loginTextStyle
import com.mib.mycompose.ui.widget.BasicTextFieldWithHint
import com.mib.mycompose.ui.widget.DisneyMainScreen
import com.mib.mycompose.ui.widget.LoginContent
import com.mib.mycompose.util.Logger
import com.mib.mycompose.viewmodel.BaseViewModel
import com.mib.mycompose.viewmodel.MainViewModel

/**
 *  author : cengyimou
 *  date : 2023/11/30 18:00
 *  description :
 */
class LoginActivity : BaseActivity(), MainViewModel.LoginPageListener {

	val mainViewModel: MainViewModel by viewModels()

	override fun createViewModel(): BaseViewModel {
		return mainViewModel
	}

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContent {
			DisneyMainScreen(mainViewModel)
		}
		mainViewModel.setLoginPageListener(this)
		setObserver()
	}

	private fun setObserver() {
		mainViewModel.loginLiveData.observe(this) { success ->
			if(success){
				MainActivity.startMainActivity(this)
			}
		}
	}

	override fun login(listId: String, password: String) {
//		toast("listId: $listId, password: $password")
		mainViewModel.login(account = listId, password = password)
	}

}

