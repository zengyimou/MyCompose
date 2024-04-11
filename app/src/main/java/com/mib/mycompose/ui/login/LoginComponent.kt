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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.mib.mycompose.R
import com.mib.mycompose.constants.Scene.MAIN_PAGE
import com.mib.mycompose.ui.theme.loginTextStyle
import com.mib.mycompose.ui.widget.BasicTextFieldWithHint
import com.mib.mycompose.util.Logger
import com.mib.mycompose.viewmodel.MainViewModel

/**
 *  author : cengyimou
 *  date : 2024/4/9 17:53
 *  description :
 */
@Preview
@Composable
fun LoginComponent(nav: NavHostController? = null, mainViewModel: MainViewModel? = viewModel()) {
	mainViewModel?.nav = nav
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

			/** 输入框*/
			var accountEditValue by remember { mutableStateOf("") }
			var passwordEditValue by remember { mutableStateOf("") }

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
					mainViewModel?.login(account = accountEditValue, password = passwordEditValue)
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


}