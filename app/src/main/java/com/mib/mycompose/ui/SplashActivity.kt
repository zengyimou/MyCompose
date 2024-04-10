package com.mib.mycompose.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role.Companion.Image
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import com.mib.mycompose.R
import com.mib.mycompose.manager.UserInfoManager
import com.mib.mycompose.util.Logger
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

/**
 *  author : cengyimou
 *  date : 2023/11/30 19:07
 *  description :
 */
@SuppressLint("CustomSplashScreen")
class SplashActivity: ComponentActivity() {

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContent {
			splashContent()
		}

		delayChange()
	}

	@SuppressLint("ComposableNaming")
	@Preview
	@Composable
	fun splashContent(){
		Surface(color = colorResource(id = R.color.purple_200),  modifier = Modifier.fillMaxSize().padding(16.dp)) {
			Image(
				painter = painterResource(id = R.drawable.icon_welcome),
				contentDescription = null,
				modifier = Modifier.padding(16.dp),
				contentScale = ContentScale.Fit
			)
		}
	}

	private fun delayChange() {
		lifecycleScope.launch {
			Logger.d(TAG, "delayChange")
			delay(2500L)
			enterApp()
		}
	}

	/**
	 * 进应用
	 */
	private fun enterApp() {
		Logger.d(TAG, "enterApp")
		val intent = Intent()
		if (UserInfoManager.isLogin) {
			intent.setClass(this, MainActivity::class.java)
		} else {
			intent.setClass(this, LoginActivity::class.java)
		}
		startActivity(intent)
		finish()
	}

	companion object{
		const val TAG = "SplashActivity"
	}
}