package com.mib.mycompose.net

import com.mib.mycompose.model.CaseListData
import com.mib.mycompose.model.LoginData
import com.mib.mycompose.request.CaseListRequest
import com.mib.mycompose.request.LoginRequest
import retrofit2.http.Body
import retrofit2.http.POST

/**
 *  author : cengyimou
 *  date : 2024/4/8 19:40
 *  description :
 */
interface Api {
	/**
	 * 登录
	 */
	@POST("user/login")
	suspend fun login(@Body request: LoginRequest): BaseResponse<LoginData>

	/**
	 * 获取案件列表
	 */
	@POST("case/case-list")
	suspend fun getCaseList(@Body request: CaseListRequest): BaseResponse<CaseListData>
}