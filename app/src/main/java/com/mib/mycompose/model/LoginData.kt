package com.mib.mycompose.model

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

/**
 * @author lindan
 * 登录响应Data
 */
@Keep
class LoginData {
    /**
     * 登录Token
     */
    var token: String? = null

    /**
     * Token到期时间戳，单位秒
     */
    @SerializedName("expire_time")
    var expireTime: String? = null

    /**
     * 雇员ID
     */
    var listId: String? = null

    /**
     * 雇员名称
     */
    var listName: String? = null

    /**
     * 雇员手机号
     */
    var listMobile: String? = null

    /**
     * 权限id
     */
    @SerializedName("role_id")
    var roleId: Int? = 0

    /**
     * 是否需要重置密码
     */
    @SerializedName("need_reset_pw")
    var needResetPw: Boolean = false

    /**
     * 判断是否为人审角色
     * 目前已不用
     */
    fun isVerifier(): Boolean{
        return roleId == 2491
    }
}