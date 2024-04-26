package com.mib.mycompose.model

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

/**
 * @author yimou
 */
@Keep
data class MainPageData(
    var connected: Content? = null,
    @SerializedName("no_connect")
    var noConnect: Content? = null,
    @SerializedName("paid_off")
    var paidOff: Content? = null,
    @SerializedName("partial_repaid")
    var partialRepaid: Content? = null,
    var uncall: Content? = null
)


@Keep
data class Content(
    @SerializedName("assign_amount")
    var assignAmount: String? = null,
    @SerializedName("case_cnt")
    var caseCnt: String? = null,
    @SerializedName("performance_amount")
    var performanceAmount: String? = null
)