package com.mib.mycompose.model

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

/**
 * @author zengyimou
 * sip 绩效
 */
@Keep
class PerformanceData {

    /**
     * 最后更新时间
     */
    @SerializedName("update_time")
    var updateTime : Long? = 0

    /**
     * 催回金额
     */
    @SerializedName("collect_amount")
    var collectAmount : String? = null

    /**
     * 分配的金额
     */
    @SerializedName("assign_amount")
    var assignAmount : String? = null

    /**
     * 催回案件
     */
    @SerializedName("collect_cases")
    var collectCases : String? = null

    /**
     * 分配的案件
     */
    @SerializedName("assign_cases")
    var assignCases : String? = null

    /**
     * 催回案件个数
     */
    @SerializedName("collect_cases_double")
    var collectCasesDouble : String? = null

    /**
     * 分配案件个数
     */
    @SerializedName("assign_cases_double")
    var assignCasesDouble : String? = null

    /**
     * 催回案件个数
     */
    @SerializedName("num_of_case_call")
    var callCaseNum : String? = null

    /**
     * 分配案件个数
     */
    @SerializedName("num_of_call")
    var callMobileNum : String? = null

    /**
     * 催回金额比
     */
    @SerializedName("collect_amount_ratio")
    var collectAmountRatio : String? = null

    /**
     * 绩效日期
     */
    @SerializedName("data_date")
    var dataDate : String? = null

    /**
     * 平均通话时长
     */
    @SerializedName("avg_duration_time")
    var avgDurationTime : Long? = 0

    /**
     * 拨打电话数(我的排名)
     */
    @SerializedName("call_mobile_num_rank")
    var callMobileNumRank : String? = "0"

    /**
     * 拨打电话数(同组最大值)
     */
    @SerializedName("call_mobile_num_team_max")
    var callMobileNumTeamMax : String? = null

    /**
     * 催收金额（我的排名）
     */
    @SerializedName("collect_amount_rank")
    var collectAmountRank : String? = "0"

    /**
     * 催收金额（同组最大值）
     */
    @SerializedName("collect_amount_team_max")
    var collectAmountTeamMax : String? = null

    /**
     * 通话时长，单位秒
     */
    @SerializedName("duration_time")
    var durationTime : Long? = 0L

    /**
     * 通话时长(我的排名)
     */
    @SerializedName("duration_time_rank")
    var durationTimeRank : String? = "0"

    /**
     * 通话时长，单位秒(同组最大值)
     */
    @SerializedName("duration_time_team_max")
    var durationTimeTeamMax : Long? = 0L

    /**
     * 最终催回率
     */
    @SerializedName("last_collect_amount_rate")
    var lastCollectAmountRate : String? = null

    /**
     * 最终催回率(我的排名)
     */
    @SerializedName("last_collect_amount_rate_rank")
    var lastCollectAmountRateRank : String? = "0"

    /**
     * 最终催回率(同组最大值)
     */
    @SerializedName("last_collect_amount_rate_team_max")
    var lastCollectAmountRateTeamMax : String? = null

    /**
     * 接通案件数
     */
    @SerializedName("num_of_succ_call")
    var numOfSuccCall : String? = null

    /**
     * 接通电话数
     */
    @SerializedName("num_of_succ_case_call")
    var numOfSuccCaseCall : String? = null

    /**
     * 发送短信个数
     */
    @SerializedName("num_of_sms")
    var numSms : String? = null

    /**
     * 在线时间
     */
    @SerializedName("online_duration")
    var onlineDuration : Long? = 0L

}