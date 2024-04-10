package com.mib.mycompose.net

import com.mib.xcredit.xcollect.network.ResponseCode
import java.io.Serializable

/**
 *  author : cengyimou
 *  date : 2024/4/8 19:44
 *  description :
 */
open class BaseResponse<T> : Serializable, ResultResponse<T> {
	var code: String? = null
	var msg: String? = null
	var data: T? = null

	override fun isSuccessful(): Boolean = code == ResponseCode.CODE_SUCCESS || code == ResponseCode.CODE_SIP_SUCCESS

	override fun getErrorCode(): String? {
		return code
	}

	override fun getErrorMessage(): String? {
		return msg
	}

	override fun getResponseData(): T? {
		return data
	}
}