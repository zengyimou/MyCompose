package com.mib.mycompose.net

import android.annotation.SuppressLint
import com.mib.mycompose.manager.UserInfoManager
import com.mib.mycompose.util.ContextHolder.context
import com.mib.mycompose.util.DeviceIdUtils
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

/**
 * @author lindan
 * 可以使用一个Interceptor来为请求添加Headers
 */
class RequestInterceptor : Interceptor {
    @SuppressLint("MissingPermission")
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val url = original.url.newBuilder().build()
        /** 最后纪录的ListId*/
        val lastListId = UserInfoManager.lastLoginListId
        /** listId*/
        val listId = if(!UserInfoManager.loginInfo?.listId.isNullOrEmpty()) UserInfoManager.loginInfo?.listId.toString()
        else lastListId
        /** 请求builder*/
        val requestBuilder = original.newBuilder()
            .addHeader("content-type", "application/json")
            .addHeader("User-Agent", "Retrofit-Collect-App")
            /** 版本名*/
            .addHeader("versionName", "3.2.0")
            /** 版本号*/
            .addHeader("versionCode", "42")
            /** 设备id*/
            .addHeader("deviceId", DeviceIdUtils.getDeviceId(context))
            /** 最后纪录的ListId*/
            .addHeader("lastListId", lastListId)
            /** listId*/
            .addHeader("listId", listId)
            .url(url)

        if (UserInfoManager.isLogin) {
            /** token*/
            requestBuilder.addHeader("token", UserInfoManager.token)
        }
        val request = requestBuilder.build()
        return chain.proceed(request)
    }
}