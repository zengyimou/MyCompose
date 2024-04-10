package com.mib.mycompose.util

import android.text.TextUtils
import com.google.gson.Gson
import org.json.JSONArray
import org.json.JSONObject
import java.lang.reflect.Type

/**
 *  author : cengyimou
 *  date : 2023/12/1 11:44
 *  description :
 */
object JsonUtils {
	private val GSON = Gson()

	/**
	 * 将JSON文本将化为指定类型对象
	 *
	 * @param json    json String
	 * @param toClass 转换的类型Class
	 * @param <T>     类型参数
	 * @return 目标类型对象
	</T> */
	fun <T> toObject(json: String, toClass: Class<T>?): T? {
		if (TextUtils.isEmpty(json) || toClass == null) {
			return null
		}
		return try {
			GSON.fromJson(json, toClass)
		} catch (e: Exception) {
			null
		}

	}

	/**
	 * 将JSON文本将化为指定类型对象
	 *
	 * @param json   json String
	 * @param toType 转换的类型Type
	 * @param <T>    类型参数
	 * @return 目标类型对象
	</T> */
	fun <T> toObject(json: String, toType: Type?): T? {
		if (TextUtils.isEmpty(json) || toType == null) {
			return null
		}
		return try {
			GSON.fromJson<T>(json, toType)
		} catch (e: Exception) {
			null
		}

	}

	/**
	 * 将任意对象转换为JSON文本
	 *
	 * @param obj 任意对象
	 * @return json String
	 */
	fun toJSON(obj: Any?): String? {
		if (obj == null) {
			return null
		}
		return try {
			GSON.toJson(obj)
		} catch (e: Exception) {
			null
		}

	}

	/**
	 * 格式化json
	 * @param response String
	 * @return String?
	 */
	fun formatDataFromJson(response: String): String? {
		try {
			if (response.startsWith("{")) {
				val jsonObject = JSONObject(response)
				return jsonObject.toString(4)
			} else if (response.startsWith("[")) {
				val jsonArray = JSONArray(response)
				return jsonArray.toString(4)
			}
		} catch (e: Exception) {
			e.printStackTrace()
		}
		return response
	}

}