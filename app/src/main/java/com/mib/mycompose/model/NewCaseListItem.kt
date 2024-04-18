package com.mib.mycompose.model

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
/**
 *  author : cengyimou
 *  date : 2021/7/13 10:28 上午
 *  description : 新版2.0案件列表
 */
@Keep
class NewCaseListItem {
    /**
     * sip通话id
     */
    @SerializedName("last_call_id")
    var callId: String? = null

    /**
     * 案件ID
     */
    @SerializedName("business_id")
    var businessId: String? = null

    /**
     * 来自哪一个产品 kashway | ipesa 只有kenya有这个字段
     */
    var product: String? = null

    /**
     * 是否展期订单
     * 0未展期 1已展期
     */
    @SerializedName("has_extend")
    var isExtended: Int? = 0

    /**
     * performance_amount
     * 绩效还款金额, 有才显示
     */
    @SerializedName("performance_amount")
    var performanceAmount: String? = null

    /**
     * 该案件当前是否属于该催收员
     */
    @SerializedName("is_self")
    var isSelf: Boolean? = false

    /**
     * 最后通话时间
     */
    @SerializedName("last_call_time")
    var lastCallTime: Int? = 0

    /**
     * 最后反馈状态文本
     */
    @SerializedName("last_contact_state_text")
    var lastContactStateText: String? = "nothing"

    /**
     * -1 为未反馈过
     */
    @SerializedName("last_contact_state")
    var lastContactState: Int? = -1

    /**
     * 最后质量反馈文本
     */
    @SerializedName("last_conversation_quality_text")
    var lastConversationQualityText: String? = "nothing"

    /**
     * 最后质量反馈
     */
    @SerializedName("last_conversation_quality")
    var lastConversationQuality: Int? = -1

    /**
     * 通话次数
     */
    @SerializedName("call_cnt")
    var callCnt: String? = null

    /**
     * 案件状态值
     */
    @SerializedName("loan_status")
    var loanStatus: String? = null

    /**
     * 案件状态显示值
     */
    @SerializedName("loan_status_text")
    var loanStatusText: String? = null

    /**
     * 客户ID
     */
    @SerializedName("customer_id")
    var customerId: String? = null

    /**
     * 客户名称
     */
    @SerializedName("customer_name")
    var customerName: String? = null

    /**上次登陆时间
     */
    @SerializedName("last_login_time")
    var lastLoginTime: Long? = 0L

    /**
     * 剩余待还金额
     */
    @SerializedName("remain_amount")
    var remainAmount: String? = null

    /**
     * 留单类型, 0为没留单, 1为今天留单, 2为昨天留单
     */
    @SerializedName("hold_case_type")
    var holdCaseType: String? = null

    /**
     * 失败信息
     */
    @SerializedName("fail_msg")
    var failMsg: String? = null

    /**
     * 失败时间
     */
    @SerializedName("fail_time")
    var failTime: Long = 0L

    /**
     * 案件类型
     * sip/sim
     */
    @SerializedName("fail_amount")
    var failAmount: String? = null

    /**
     * callback时间
     */
    @SerializedName("reminder_time")
    var reminderTime: Int = 0

    /**
     * 订单渠道来源
     */
    val channel: String? = null

    /**
     * 算法 案件标签
     */
    @SerializedName("case_tag")
    val divisionTags: MutableList<String> = mutableListOf()

    /**
     * 客户案件标签
     */
    @SerializedName("product_scheme")
    var productScheme: String? = null

    /**
     * 最长逾期期数
     */
    @SerializedName("longest_overdue_index")
    var longestOverdueIndex: Int? = 0

    /**
     * 逾期天数
     */
    @SerializedName("overdue_days")
    var overdueDays: Int? = 0

    /**
     * 是否为新户
     */
    @SerializedName("customer_type")
    var customerType: String? = null

    /**
     * 是否为新户
     */
    @SerializedName("loan_cnt")
    var loanTimes: Int = 0


    /** 分组相关 */
    /**
     * 本组总案件数
     */
    var totalCaseNum: Int? = 1
    /**
     * caseNum
     */
    var caseNum: Int? = 1
    /**
     * 是否为组头
     */
    var isGroupFirst = true

    /**
     * 是否为组尾
     */
    var isGroupLast = true

}

@Keep
class NewCaseGroupListItem {
    @SerializedName("customer_id")
    var customerId: String? = ""
    @SerializedName("customer_name")
    var customerName: String? = ""
    @SerializedName("group_case_list")
    var caseList: List<NewCaseListItem>? = null
    @SerializedName("group_id")
    var groupId: String? = ""

    companion object{
        const val HOLD_TODAY  = 1
        const val HOLD_YESTERDAY = 2
        /**
         * 制造组数据
         * @param list List<NewCaseGroupListItem>
         * @return MutableList<NewCaseListItem>
         */
        fun makeCaseGroupList(list: List<NewCaseGroupListItem>?): MutableList<NewCaseListItem> {
            val caseList = mutableListOf<NewCaseListItem>()
            if(!list.isNullOrEmpty()){
                list.forEach { group ->
                    if(!group.caseList.isNullOrEmpty()){
                        val totalCaseNum = group.caseList!!.size
                        group.caseList!!.forEachIndexed { index, case ->
                            case.totalCaseNum = totalCaseNum
                            case.caseNum = index + 1
                            if(group.caseList!!.size == 1){
                                case.isGroupFirst = true
                                case.isGroupLast = true
                            }else if(index == 0){
                                case.isGroupFirst = true
                                case.isGroupLast = false
                            }else if(index == (group.caseList!!.size - 1)){
                                case.isGroupFirst = false
                                case.isGroupLast = true
                            }else {
                                case.isGroupFirst = false
                                case.isGroupLast = false
                            }
                            caseList.add(case)
                        }
                    }
                }
            }
            return caseList
        }
    }
}