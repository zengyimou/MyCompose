package com.mib.mycompose.net

/**
 *  author : cengyimou
 *  date : 2024/4/8 19:36
 *  description :
 */
interface ResultResponse <T> {
	/**
	 * 获取数据主体
	 */
	fun getResponseData(): T?

	/**
	 * 响应是否成功
	 */
	fun isSuccessful(): Boolean

	/**
	 * 错误错误响应码
	 */
	fun getErrorCode(): String?

	/**
	 * 获取错误的信息
	 */
	fun getErrorMessage(): String?
}