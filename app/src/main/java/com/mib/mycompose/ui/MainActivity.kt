package com.mib.mycompose.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.jeremyliao.liveeventbus.LiveEventBus
import com.mib.mycompose.R
import com.mib.mycompose.event.Event
import com.mib.mycompose.event.LogoutEvent
import com.mib.mycompose.ui.widget.SimpleText
import com.mib.mycompose.ui.widget.SimpleTextFieldComponent
import com.mib.mycompose.ui.widget.StyleText
import com.mib.mycompose.ui.theme.MyComposeTheme


class MainActivity : ComponentActivity() {
	private var context: Context? = null
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		context = this
		setObserver()
		setContent {
			MyComposeTheme {
				// A surface container using the 'background' color from the theme
				Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
					Greeting("Android")
				}
			}
		}
	}

	private fun setObserver() {
		LiveEventBus.get<LogoutEvent>(Event.EVENT_LOGOUT).observe(this){
			logoutPage()
		}
	}

	private fun logoutPage() {
		val intent = Intent(this, LoginActivity::class.java)
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
		startActivity(intent)
		finish()
	}

	companion object{
		fun startMainActivity(context: Context) {
			val intent = Intent(context, MainActivity::class.java)
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
			context.startActivity(intent)
		}
	}
}

@Preview
@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
	var count by remember { mutableStateOf(10) }
	Column {
		Text(
			text = "Hello $name!",
			modifier = modifier
		)
		Text(
			text = "Hello $name! ${count}",
			modifier = modifier
		)
		Button(onClick = {
			count += 1
			Log.d("", "点击·······${count}")
		}) {
			Text(
				text = "Hello $name!",
				modifier = modifier
			)
		}
		//普通text
		SimpleText(text = "text")
		StyleText(displayText = "displayText", style = TextStyle(
			fontSize = 20.sp,
			color = colorResource(id = R.color.purple_200)
		))

		SimpleTextFieldComponent()

	}
}

@Preview
@Composable
fun GreetingPreview() {
	MyComposeTheme {
		Greeting("Android")
	}
}