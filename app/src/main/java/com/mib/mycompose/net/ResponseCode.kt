package com.mib.xcredit.xcollect.network

/**
 * @author lindan
 * 响应状态枚举
 */
object ResponseCode {
    /**
     * {1: '系统错误', 2: '错误', 12: '通话模块异常', 2011: '当前已经反馈了', 122: '通话反馈异常', 1221: '催收员上一次通话还没记录，不能开始新的通话', 121: '开始通话异常', 13: '案件错误', 1311: '没有接单权限,请联系管理员', 131: '获取案件错误', 101: '金额不合法', 100: '缺少参数', 10: '参数错误', 102: '排序字段不合法', 141: 'Sms渠道错误', 14: '短信错误', 1134: '当前用户密码不是初始密码', 1131: '不合规的密码', 116: '用户无效', 111: '登录异常', 1111: '请先登录', 1112: '催收员不允许在这个设备上登录', 113: '密码错误', 1132: '密码不能等于账号', 1133: '两次输入的新密码不一样', 1135: '密码错误次数超过上限, 账户已经被停用', 114: '用户添加失败', 1122: '用户已经存在', 11: '用户失败', 1121: '催收员不存在', 112: '没有权限', 115: '用户校验失败', 153: '调用催收内部系统失败', 151: '调用Loan系统失败', 15: '调用其他系统失败', 152: '调用SIP系统失败'}
     */

    /**
     * 响应成功
     */
    const val CODE_SUCCESS = "0"

    /**
     * 响应成功 sip服务器
     */
    const val CODE_SIP_SUCCESS = "0000"

    /**
     * 用户Token过期或失效，需要重新登录
     */
    const val CODE_TOKEN_OUT = "1111"

    /**
     * 找不到催收员
     */
    const val CODE_LOGOUT_ERROR = "1121"

    /**
     * 用户Token过期或失效，需要重新登录
     */
    const val CASE_TOKEN_OUT = "6001"

    /**
     * 用户Token过期或失效，需要重新登录
     */
    const val CODE_NO_GET_CASE_PERMISSION = "6002"

    /**
     * 存在未提交的案件
     */
    const val CODE_UN_SUBMIT_LAST_CASE = "4001"

    /**
     * 未知Code
     */
    const val CODE_UNKNOWN = "-1"
}