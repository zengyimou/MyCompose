package com.mib.mycompose.model

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

/**
 * @author lindan
 * 催收员信息响应Data
 */
@Keep
class EmployeeInfoData {
    /**
     * 雇员ID
     */
    @SerializedName("list_id")
    var listId : String? = null

    /**
     * 雇员名称
     */
    @SerializedName("list_name")
    var listName : String? = null

    /**
     * 雇员手机号
     */
    @SerializedName("list_mobile")
    var listMobile : String? = null

    /**
     * 权限id
     */
    @SerializedName("role_id")
    var roleId: Int? = 0
}