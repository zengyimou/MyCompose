package com.mib.mycompose.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mib.mycompose.net.ApiException
import com.mib.mycompose.net.ResponseState
import com.mib.mycompose.net.ResultResponse
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

/**
 *  author : cengyimou
 *  date : 2024/4/8 19:27
 *  description :
 */
open class BaseViewModel: ViewModel() {

	val responseStateLiveData = MutableLiveData<ResponseState>()
	val throwableLiveData = MutableLiveData<Throwable>()

	/**
	 * 获取数据
	 * @param block 获取数据的行为模式
	 * @param onSuccess 行为成功结果处理
	 * @param onError 行为失败结果处理，如为null或返回false，则执行默认异常处理方法
	 * @param onComplete 行为最终的处理，不管成功或失败均会执行，如为null或返回false，则执行默认终结处理方法
	 */
	open fun <T> retrieveData(
		block: suspend CoroutineScope.() -> ResultResponse<T>,
		onSuccess: (data: T?) -> Unit = {},
		onError: ((e: Throwable) -> Boolean)? = null,
		onComplete: (() -> Boolean)? = null
	) {
		viewModelScope.launch(CoroutineExceptionHandler { _, throwable ->
			val handleErrorResult = onError?.invoke(throwable) ?: false
			if (!handleErrorResult) {
				onError(throwable)
			}
		}) {
			try {
				val response: ResultResponse<T> = block.invoke(this)
				if (response.isSuccessful()) {
					onSuccess(response.getResponseData())
				} else {
					val apiException =
						ApiException(response.getErrorCode() ?: "", response.getErrorMessage())
					val handleErrorResult = onError?.invoke(apiException) ?: false
					if (!handleErrorResult) {
						onError(apiException)
					}
				}
			} finally {
				val handleCompleteResult = onComplete?.invoke() ?: false
				if (!handleCompleteResult) {
					onCompleted()
				}
			}
		}
	}

	/**
	 * 默认的异常处理方法
	 */
	open fun onError(throwable: Throwable) {
		throwableLiveData.value = throwable
	}

	/**
	 * 默认的处理终结方法
	 */
	open fun onCompleted() {
		responseStateLiveData.value = ResponseState.TYPE_UNKNOWN
	}
}