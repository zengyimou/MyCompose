package com.mib.mycompose.model

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName


/**
 * @author cengyimou
 * sipcase列表
 */
@Keep
open class CaseListData{
    /**
     * 当前页码
     */
    @SerializedName("page_no")
    var pageNo: Int = 1

    /**
     * 总条数
     */
    var total: Int = 0

    /**
     * 列表数据
     */
    @SerializedName("case_list")
    var caseList: List<NewCaseGroupListItem>? = null

    /**
     * 下一步功能用的id列表
     */
    @SerializedName("index_list")
    var pkList: List<Nexts>? = null
}

@Keep
class Nexts{
    @SerializedName("business_id")
    var businessId: String? = null

    var product: String? = null
}