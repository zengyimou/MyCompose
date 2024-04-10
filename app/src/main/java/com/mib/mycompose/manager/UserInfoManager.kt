package com.mib.mycompose.manager

import android.content.Intent
import android.text.TextUtils
import com.mib.mycompose.model.LoginData
import com.mib.mycompose.util.ContextHolder
import com.mib.mycompose.util.JsonUtils
import com.mib.mycompose.util.PreferencesSecurity
import com.mib.mycompose.util.SPUtils

/**
 * 用户信息管理类
 *
 * @author lindan
 */
object UserInfoManager {
	private const val TAG = "UserInfoManager"
	var loginInfo: LoginData? = null
		private set

	val isLogin: Boolean
		get() = !loginInfo?.token.isNullOrEmpty()

	val token: String
		get() = loginInfo?.token ?: ""

	val LAST_LOGIN_LISTID = "LAST_LOGIN_LISTID"

	var lastLoginListId = ""


	/**
	 * 初始化
	 */
	fun init() {
		val jsonString = PreferencesSecurity.accountInfo
		if (!TextUtils.isEmpty(jsonString)) {
			loginInfo = JsonUtils.toObject(jsonString, LoginData::class.java)
			lastLoginListId = SPUtils.getSharedStringData(
				ContextHolder.context, LAST_LOGIN_LISTID,
				"000000"
			)
		}
	}

	/**
	 * 设置登录用户信息
	 */
	fun setLoginInfo(loginInfo: LoginData) {
		SPUtils.setSharedStringData(ContextHolder.context, LAST_LOGIN_LISTID, loginInfo.listId ?: "000000")
		lastLoginListId = loginInfo.listId ?: "000000"
		UserInfoManager.loginInfo = loginInfo
		PreferencesSecurity.accountInfo = JsonUtils.toJSON(loginInfo) ?: ""
	}

	/**
	 * 保存最新
	 */
	fun reSave() {
		PreferencesSecurity.accountInfo = if (null != loginInfo) JsonUtils.toJSON(loginInfo!!)
			?: "" else ""
	}

	/**
	 * 登出
	 */
	fun logout() {
		PreferencesSecurity.accountInfo = ""
		loginInfo = null
	}

}