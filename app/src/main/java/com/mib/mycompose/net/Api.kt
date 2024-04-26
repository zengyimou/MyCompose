package com.mib.mycompose.net

import com.mib.mycompose.model.CaseListData
import com.mib.mycompose.model.EmployeeInfoData
import com.mib.mycompose.model.LoginData
import com.mib.mycompose.model.MainPageData
import com.mib.mycompose.model.PerformanceData
import com.mib.mycompose.request.CaseListRequest
import com.mib.mycompose.request.LoginRequest
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.QueryMap

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


	@GET("user/user-info")
	suspend fun getEmployeeInfo(@QueryMap request: Map<String, String>): BaseResponse<EmployeeInfoData>
	/**
	 * 获取案件列表
	 */
	@POST("case/case-list")
	suspend fun getCaseList(@Body request: CaseListRequest): BaseResponse<CaseListData>

	/**
	 * 催收员的绩效数据
	 */
	@GET("performance/index")
	suspend fun getEmployeePerformance(): BaseResponse<PerformanceData>

	/**
	 * 首页显示数据
	 */
	@GET("case/case-statistic")
	suspend fun getCaseStatistic(): BaseResponse<MainPageData>
}