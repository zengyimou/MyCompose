package com.mib.mycompose.ui.me

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.currentRecomposeScope
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.Snapshot
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.jeremyliao.liveeventbus.LiveEventBus
import com.mib.mycompose.BuildConfig
import com.mib.mycompose.R
import com.mib.mycompose.event.Event
import com.mib.mycompose.event.LogoutEvent
import com.mib.mycompose.ext.toast
import com.mib.mycompose.manager.UserInfoManager
import com.mib.mycompose.ui.theme.C_111111
import com.mib.mycompose.ui.theme.C_222222
import com.mib.mycompose.ui.theme.C_5AEA58
import com.mib.mycompose.ui.theme.C_999999
import com.mib.mycompose.ui.theme.FF999999
import com.mib.mycompose.ui.widget.DrawCircle
import com.mib.mycompose.ui.widget.MainBackHandler
import com.mib.mycompose.ui.widget.SettingItem
import kotlinx.coroutines.launch

/**
 *  author : cengyimou
 *  date : 2024/4/11 10:33
 *  description :
 */
@Preview
@Composable
fun MePage(modifier: Modifier = Modifier, navHostController: NavHostController = rememberNavController()) {
	var showDialog by remember { mutableStateOf(false) }
	ConstraintLayout(
		modifier = Modifier
			.fillMaxWidth()
			.fillMaxHeight()
			.background(Color.White),
	) {
//		val recompose = currentRecomposeScope
//		recompose.invalidate()
//		Snapshot.takeSnapshot()
		val (ivHeader, tvName, tvId, ivRoundPoint, tvSipStatus,
			settingNetwork, settingUpload, settingCallLog, settingChangePwd, settingWhatsapp, settingSms,
			settingReminder, tvVersion, btnLogout) = createRefs()

		val context = LocalContext.current

		Image(
			painter = painterResource(id = R.mipmap.icon_header),
			contentDescription = null,
			modifier = Modifier
				.constrainAs(ivHeader) {
					top.linkTo(parent.top, margin = 32.dp)
					start.linkTo(parent.start, margin = 16.dp)
				}
				.padding(end = 5.dp)
				.height(50.dp)
				.width(50.dp),
		)
		Text(
			text = UserInfoManager.loginInfo?.listName ?: "", fontSize = 20.sp, color = Color.Black,
			modifier = Modifier.constrainAs(tvName) {
				top.linkTo(ivHeader.top)
				start.linkTo(ivHeader.end, margin = 16.dp)
				end.linkTo(parent.end, margin = 16.dp)
				width = Dimension.fillToConstraints
			},
			maxLines = 1,
			softWrap = false,
			overflow = TextOverflow.Ellipsis,
		)

		Text(text = "ID: ${UserInfoManager.loginInfo?.listId}", fontSize = 12.sp, color = C_999999,
			modifier = Modifier.constrainAs(tvId) {
				top.linkTo(tvName.bottom, 8.dp)
				start.linkTo(tvName.start)
				end.linkTo(parent.end, margin = 16.dp)
				width = Dimension.fillToConstraints
			})

		DrawCircle(
			modifier = Modifier
				.constrainAs(ivRoundPoint) {
					top.linkTo(ivHeader.bottom, 54.dp)
					start.linkTo(parent.start, 16.dp)
				},
			color = C_5AEA58,
			canvasSize = 10.dp,
		)

		Text(
			text = "Connect",
			fontSize = 14.sp,
			color = C_222222,
			modifier = Modifier.constrainAs(tvSipStatus) {
				start.linkTo(ivRoundPoint.end, 20.dp)
				top.linkTo(ivRoundPoint.top)
				end.linkTo(parent.end, 16.dp)
				bottom.linkTo(ivRoundPoint.bottom)
				width = Dimension.fillToConstraints
			}
		)

		SettingItem(
			modifier = Modifier.constrainAs(settingNetwork) {
				top.linkTo(tvSipStatus.bottom, 16.dp)
				start.linkTo(parent.start, 16.dp)
				end.linkTo(parent.end, 16.dp)
				width = Dimension.fillToConstraints
			},
			title = "Network Test",
			res = R.mipmap.icon_network
		) {
			context.toast("Network Test")
		}

		SettingItem(
			modifier = Modifier.constrainAs(settingUpload) {
				top.linkTo(settingNetwork.bottom, 8.dp)
				start.linkTo(parent.start, 16.dp)
				end.linkTo(parent.end, 16.dp)
				width = Dimension.fillToConstraints
			},
			title = "Upload Certificate",
			res = R.mipmap.icon_upload
		) {
			context.toast("Upload Certificate")
		}

		SettingItem(
			modifier = Modifier.constrainAs(settingChangePwd) {
				top.linkTo(settingUpload.bottom, 8.dp)
				start.linkTo(parent.start, 16.dp)
				end.linkTo(parent.end, 16.dp)
				width = Dimension.fillToConstraints
			},
			title = "Change Password",
			res = R.mipmap.icon_password
		) {
			context.toast("Change Password")
		}

		SettingItem(
			modifier = Modifier.constrainAs(settingWhatsapp) {
				top.linkTo(settingChangePwd.bottom, 8.dp)
				start.linkTo(parent.start, 16.dp)
				end.linkTo(parent.end, 16.dp)
				width = Dimension.fillToConstraints
			},
			title = "Whatsapp Records",
			res = R.mipmap.icon_chat
		) {
			context.toast("Whatsapp Records")
		}

		Text(text = "v ${BuildConfig.VERSION_NAME}", color = C_999999,
			fontSize = 14.sp, modifier = Modifier.constrainAs(tvVersion) {
				top.linkTo(settingWhatsapp.bottom, 16.dp)
				start.linkTo(parent.start, 16.dp)
				end.linkTo(parent.end, 16.dp)
			})

		Button(
			onClick = {
				showDialog = true
			},
			modifier = Modifier
				.constrainAs(btnLogout) {
					top.linkTo(tvVersion.bottom)
					start.linkTo(parent.start, 16.dp)
					end.linkTo(parent.end, 16.dp)
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
				Text(text = "Logout", color = colorResource(id = R.color.white))
			}
		}
		if(showDialog){
			AlertDialog(
				title = { Text(text = "Are you sure logout?") },
				onDismissRequest = { showDialog = false },
				confirmButton = { Text(text = "confirm", modifier = Modifier.clickable {
					showDialog = false
					LiveEventBus.get<LogoutEvent>(Event.EVENT_LOGOUT).post(LogoutEvent())
				}) },
				dismissButton = { Text(text = "cancel", modifier = Modifier.clickable {
					showDialog = false
				}) }
			)
		}
	}
	MainBackHandler()
}
