package com.mib.mycompose.net

import com.jeremyliao.liveeventbus.LiveEventBus
import com.jeremyliao.liveeventbus.core.LiveEventBusCore
import com.mib.mycompose.R
import com.mib.mycompose.event.Event
import com.mib.mycompose.event.LogoutEvent
import com.mib.mycompose.model.LoginData
import com.mib.mycompose.request.LoginRequest
import com.mib.mycompose.retrofit.RetrofitFactory
import com.mib.mycompose.util.ToastUtils
import com.mib.xcredit.xcollect.network.ResponseCode

/**
 *  author : cengyimou
 *  date : 2024/4/9 09:59
 *  description :
 */
object RequestHolder {

	val api = RetrofitFactory.createApi(Api::class.java)

	/**
	 * 公用方法检查token
	 * @param response BaseResponse<T>?
	 * @return BaseResponse<T>
	 */
	private fun <T> tokenCheck(response: BaseResponse<T>?): BaseResponse<T> {
		return if (response?.isSuccessful() == true) {
			response
		} else {
			when (response?.code) {
				ResponseCode.CODE_TOKEN_OUT -> {
					ToastUtils.showShort(R.string.expired_token)
					LiveEventBus.get<LogoutEvent>(Event.EVENT_LOGOUT).post(LogoutEvent())
					throw ApiException(response.code, response.msg)
				}
				ResponseCode.CODE_LOGOUT_ERROR -> {
					// 请求注销失败 手动注销应用
					LiveEventBus.get<LogoutEvent>(Event.EVENT_LOGOUT).post(LogoutEvent())
					throw ApiException(response.code, response.msg)
				}
				else -> {
					throw ApiException(response?.code, response?.msg)
				}
			}
		}
	}

	/**
	 * 登录
	 * @param listId 催收员Id
	 * @param password 密码
	 */
	suspend fun login(listId: String, password: String): BaseResponse<LoginData> {
		return tokenCheck(
			api.login(
				LoginRequest(
					listId,
					password
				)
			)
		)
	}


}