package com.mib.mycompose.model

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

/**
 *  author : cengyimou
 *  date : 2021/7/7 2:29 下午
 *  description : 新版的案件详情
 */
@Keep
data class NewCaseItem(
    @SerializedName("case_type")
    val caseType: String? = null,
    @SerializedName("call_id")
    val callId: String? = null,
    val case: Case? = null,
    @SerializedName("is_self")
    val isSelf: Boolean? = false,
    @SerializedName("reminder_time")
    var reminderTime: Int? = 0,
    val performance: PerformanceItem? = null,
    val user: User? = null,
    /**
     * SIP电话回拨实体
     */
    @SerializedName("callback_contact")
    val callbackContact: CallbackContact? = null,  //用于回拨功能获取回拨者的通讯录信息，提交反馈使用
) {
    val isValid: Boolean
        get() = !case?.businessId.isNullOrEmpty()
}

@Keep
data class Case(
    @SerializedName("customer_name")
    val customerName: String? = null,
    @SerializedName("due_date")
    val dueDate: String? = null,
    @SerializedName("has_extend")
    val hasExtend: Int = 0,
    @SerializedName("lend_amount")
    val lendAmount: String? = null,
    @SerializedName("limit_day")
    val limitDay: Int? = 0,
    @SerializedName("loan_amount")
    //案件本金
    val loanAmount: String? = null,
    @SerializedName("business_id")
    val businessId: String? = null,
    @SerializedName("loan_status")
    val loanStatus: String? = "0",
    @SerializedName("loan_status_text")
    val loanStatusText: String? = null,
    //逾期产生的利息, 也就是罚息
    @SerializedName("overdue_amount")
    val overdueAmount: String? = null,
//    @SerializedName("overdue_day")
//    val overdueDay: Int? = 0,
    val oxxo: String? = null,
    val product: String? = null,
    @SerializedName("remain_amount")
    val remainAmount: String? = null,
    @SerializedName("repay_code")
    val repayCode: String? = null,
    @SerializedName("repayment_amount")
    val repaymentAmount: String? = null,
    @SerializedName("aigo_score")
    val recallScore: String? = null,

    //缺失
    @SerializedName("loan_date")
    val loanDate: String? = null,
    @SerializedName("extend_count")
    val extendCount: String? = null,
    @SerializedName("customer_type")
    val customerType: String? = null,
    @SerializedName("product_type")
    val loanTerm: String? = null,
    @SerializedName("remain_overdue_amount")
    val remainOverdueAmount: String? = null,
    @SerializedName("overdue_days")
    val overdueDays: Int = 0,
    val periods: String? = null,
    @SerializedName("case_tag")
    val divisionTags: MutableList<String> = mutableListOf(),
    @SerializedName("case_remark")
    val divisionReason: String? = null,
    /** 展期失败原因,空则说明可以展期*/
    @SerializedName("extend_case_fail_reason")
    val extendCaseFailReason: String? = null,

    //免罚息
    /** 减免罚息天数（实际上的数据是时间戳）*/
    @SerializedName("no_penalty_days")
    val noPenaltyDays: Long = 0L,
    /** 今日罚息*/
    @SerializedName("today_penalty_amount")
    val todayPenaltyAmount: String? = null,
    /** 减免罚息*/
    @SerializedName("total_penalty_amount")
    val totalPenaltyAmount: String? = null,

    /** 客户案件标签*/
    @SerializedName("product_scheme")
    var productScheme: String? = null,
    /** 是否有待还利息可用 1为有可用*/
    @SerializedName("early_settlement_status")
    var earlySettlementStatus: Int? = 0,
    //2023/11/22新增
    @SerializedName("penalty_interest")
    val penaltyInterest: String? = null,
    @SerializedName("late_payment")
    val latePayment: String? = null,
    @SerializedName("excise_duty")
    val exciseDuty: String? = null,
)

@Keep
data class PerformanceItem(
    val amount: String? = null,
    @SerializedName("end_time")
    val endTime: Long? = 0L,
    @SerializedName("end_time_config")
    val endTimeConfig: Long? = 0L,
    @SerializedName("start_time")
    val startTime: Long = 0L
)

@Keep
data class User(
    val address: String? = null,
    val birthday: String? = null,
    @SerializedName("customer_id")
    val customerId: String? = null,
    @SerializedName("enable_user_info_display")
    val enableUserInfoDisplay: Boolean? = false,
    @SerializedName("last_login_time")
    val lastLoginTime: Long? = 0L,
    @SerializedName("loan_cnt")
    val loanCnt: Int? = 0,
    @SerializedName("national_id")
    val nationalId: String? = null,
    @SerializedName("overdue_days_range")
    val overdueDayRange: String? = null,
    val product: String? = null,
    @SerializedName("user_gender")
    val userGender: String? = null,
    @SerializedName("user_name")
    val userName: String? = null,
    @SerializedName("customer_mobile")
    val customerMobile: String? = null,
    /**
     * 订单渠道来源
     */
    val channel: String? = null,
    @SerializedName("tag_id")
    val tagId: String? = null,
) {
    @SerializedName("display_key")
    val displayKey: Array<String> = arrayOf()
}

@Keep
data class CallbackContact(
    var mobile: String? = null,
    @SerializedName("is_close_relation")
    var isCloseRelation: Int? = 0,
    @SerializedName("is_emergency_contact")
    var isEmergencyContact: Int? = 0,
    var relation: String? = null,
    @SerializedName("relation_name")
    var relationName: String? = null,
    @SerializedName("contact_type")
    var contactType: Int? = 0,
)