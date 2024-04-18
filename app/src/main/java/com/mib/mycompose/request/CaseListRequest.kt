package com.mib.mycompose.request

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

/**
 * @author lindan
 * 案件列表请求
 */
@Keep
data class CaseListRequest(
    @SerializedName("alert_type")
    var alertType: Int? = null,
    @SerializedName("case_type_set")
    var caseTypeSet: List<Int>? = null,
    @SerializedName("contact_state_type")
    var contactStateType: List<ContactStateType>? = null,
    @SerializedName("conversation_quality_state")
    var conversationQualityState: ConversationQualityState? = null,
    @SerializedName("customer_mobile")
    var customerMobile: String? = null,
    @SerializedName("customer_name")
    var customerName: String? = null,
    @SerializedName("page_no")
    var pageNo: Int? = null,
    @SerializedName("page_size")
    var pageSize: Int? = null,
    /** 列表类型1已支付 0未支付*/
    @SerializedName("performance_type")
    var performanceType: Int? = null,
    @SerializedName("product_scheme")
    var productScheme: List<ProductSchemeRequest>? = null,
    @SerializedName("repay_status")
    var repayStatus: Int? = null,

    @SerializedName("today_call_num")
    var callingTimesRange: List<String>? = null,
    @SerializedName("today_sms_num")
    var messagingTimesRange: List<String>? = null,
)

@Keep
class ProductSchemeRequest {
    var product: String? = null

    @SerializedName("product_scheme")
    var productScheme: List<String>? = mutableListOf()
}

@Keep
data class ConversationQualityState(
    @SerializedName("state_list")
    var stateList: List<Int>? = null,
    @SerializedName("time_range")
    var timeRange: String? = null,
)

@Keep
data class ContactStateType(
    @SerializedName("contact_type")
    var contactType: Int? = 0,
    @SerializedName("state_list")
    var stateList: List<Int>? = null,
)
