package com.mib.mycompose.request

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

/**
 *  author : cengyimou
 *  date : 2024/4/8 19:43
 *  description :
 */
@Keep
class LoginRequest(
	/**催收员ID*/
	@SerializedName("list_id") val listId: String,
	/**密码*/
	val password: String
)