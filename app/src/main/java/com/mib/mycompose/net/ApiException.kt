package com.mib.mycompose.net

/**
 *  author : cengyimou
 *  date : 2024/4/8 19:37
 *  description :
 */
class ApiException(val code: String?, val msg: String?) : Throwable(msg)