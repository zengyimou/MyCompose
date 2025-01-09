package com.mib.mycompose.ui.login

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
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.mib.mycompose.R
import com.mib.mycompose.constants.C.LINK_TAG
import com.mib.mycompose.ext.toast
import com.mib.mycompose.ui.theme.loginTextStyle
import com.mib.mycompose.ui.widget.BasicTextFieldWithHint
import com.mib.mycompose.ui.widget.CircularProgressIndicator
import com.mib.mycompose.ui.widget.NavScreen
import com.mib.mycompose.util.Logger
import com.mib.mycompose.util.RouteUtils.navigateStart

/**
 *  author : cengyimou
 *  date : 2024/4/9 17:53
 *
 *  compose note :
 *
 *  LaunchedEffect = 启动挂起函数（协程），用于一次性任务或基于 key 变化的任务。 适合：网络请求、延迟操作、触发动画等。
 *	DisposableEffect = 创建并清理需要生命周期管理的副作用，例如监听器、广播接收器。适合：监听事件、注册和注销资源、设备状态监控等。
 *  SideEffect = 处理轻量级的副作用，通常用于调试或同步非协程的任务。 适合：日志记录、更新外部依赖、调试等
 *
 *
 *  description :
 */
@Preview
@Composable
fun LoginPage(navHostController: NavHostController? = null, loginViewModel: LoginViewModel = viewModel()) {

	val context = LocalContext.current
	val loginState = loginViewModel.loginLiveData.observeAsState()
	val throwableState = loginViewModel.throwableLiveData.observeAsState()

	var btnClickState by remember { mutableStateOf(false) }

	/** 输入框*/
	var accountEditValue by remember { mutableStateOf("") }
	var passwordEditValue by remember { mutableStateOf("") }

	var showLoading by remember { mutableStateOf(false) }

	//请求失败toast
	LaunchedEffect(key1 = throwableState.value) {
		showLoading = false
		if (throwableState.value?.message?.isNotEmpty() == true) {
			context.toast(throwableState.value?.message.toString())
		}
	}

	//登录成功跳转
	LaunchedEffect(key1 = loginState.value) {
		if (loginState.value == true) {
			navHostController?.navigateStart(routeName = NavScreen.TabMain.route)
		}
	}

	LaunchedEffect(key1 = btnClickState) {
		if (btnClickState) {
			showLoading = true
			loginViewModel.login(account = accountEditValue, password = passwordEditValue)
			btnClickState = false
		}
	}

	DisposableEffect(Unit) {
		onDispose {
			showLoading = false
			Logger.d(LINK_TAG, "LoginComponent DisposableEffect!!!${loginViewModel.hashCode()}")
		}
	}

	Box(
		modifier = Modifier
			.fillMaxSize()
			.background(colorResource(id = R.color.white))
	) {
		ConstraintLayout(
			modifier = Modifier
				.absolutePadding(top = 40.dp, left = 16.dp, right = 16.dp)
				.fillMaxWidth()
				.height(IntrinsicSize.Min)
				.background(colorResource(id = R.color.white)),
		) {

			val (
				tvAccount,
				etAccount,
				tvPassword,
				etPassword,
				iconWelcome,
				btnLogin,
			) = createRefs()
			/** welcome Icon*/
			Image(
				painter = painterResource(id = R.drawable.icon_welcome),
				contentDescription = null,
				modifier = Modifier
					.constrainAs(iconWelcome) {
						top.linkTo(parent.top)
						start.linkTo(parent.start)
						end.linkTo(parent.end)
					}
					.padding(16.dp)
					.fillMaxWidth(0.7f)
					.wrapContentHeight(),
				contentScale = ContentScale.Fit
			)

			Text(
				text = "Account",
				modifier = Modifier
					.constrainAs(tvAccount) {
						top.linkTo(iconWelcome.bottom)
						start.linkTo(parent.start)
						end.linkTo(parent.end)
					}
					.padding(top = 10.dp)
					.fillMaxWidth()
					.absolutePadding(top = 40.dp),
				style = loginTextStyle,
			)

			BasicTextFieldWithHint(
				hint = "Enter Account",
				onValueChange = { value ->
					Logger.d("LoginContent", "onValueChange: $value")
					accountEditValue = value
				},
				modifier = Modifier
					.constrainAs(etAccount) {
						top.linkTo(tvAccount.bottom)
						start.linkTo(tvAccount.start)
						end.linkTo(parent.end)
					}
					.padding(top = 6.dp)
					.fillMaxWidth()
					.background(Color.Red),
			)

			Text(
				text = "Password",
				modifier = Modifier
					.constrainAs(tvPassword) {
						top.linkTo(etAccount.bottom)
						start.linkTo(tvAccount.start)
						end.linkTo(parent.end)
					}
					.padding(top = 6.dp)
					.fillMaxWidth(),
				style = loginTextStyle,
				textAlign = TextAlign.Start
			)

			BasicTextFieldWithHint(
				hint = "Enter Password",
				onValueChange = { value ->
					Logger.d("LoginContent", "onValueChange: $value")
					passwordEditValue = value
				},
				modifier = Modifier
					.constrainAs(etPassword) {
						top.linkTo(tvPassword.bottom)
						start.linkTo(tvAccount.start)
						end.linkTo(parent.end)
					}
					.fillMaxWidth()
					.background(Color.Transparent)
					.padding(top = 10.dp)
					.background(Color.Red),
				isPassword = true
			)

			Button(
				onClick = {
					// 处理按钮点击事件
					btnClickState = true
				},
				modifier = Modifier
					.constrainAs(btnLogin) {
						top.linkTo(etPassword.bottom)
						start.linkTo(tvAccount.start)
						end.linkTo(parent.end)
					}
					.padding(horizontal = 16.dp, vertical = 40.dp)
					.fillMaxWidth()
					.height(50.dp),
				colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.colorPrimary)), // 设置按钮颜色
				shape = RoundedCornerShape(8.dp) // 设置背景圆角
			) {
				// 按钮内容
				Box(
					modifier = Modifier
						.fillMaxWidth()
						.fillMaxHeight()
						.background(Color.Transparent),
					contentAlignment = Alignment.Center
				) {
					Text(text = "Login", color = colorResource(id = R.color.white))
				}
			}

		}
	}
	CircularProgressIndicator(isShow = showLoading)
}