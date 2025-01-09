package com.mib.mycompose.ui.case

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import androidx.paging.PagingState
import androidx.paging.cachedIn
import com.mib.mycompose.base.Constants
import com.mib.mycompose.model.NewCaseGroupListItem
import com.mib.mycompose.model.NewCaseListItem
import com.mib.mycompose.net.RequestHolder
import com.mib.mycompose.net.ResponseState
import com.mib.mycompose.ui.case.CaseViewModel.Companion.TAG
import com.mib.mycompose.util.Logger
import com.mib.mycompose.viewmodel.BaseViewModel

/**
 *  author : cengyimou
 *  date : 2024/4/17 10:18
 *  description :
 */
class CaseViewModel : BaseViewModel() {
	var test = 0

	val caseList = MutableLiveData<List<NewCaseListItem>>()
	/** 当前请求页数*/
	var pageNo = 0

	var noMoreData = MutableLiveData<Boolean>()
		private set

	var isRefreshing = MutableLiveData<Boolean>()

	var isRequestNow = false

	fun getCaseList(isRefresh: Boolean) {
		if(isRequestNow) return
		isRequestNow = true
		if(isRefresh) isRefreshing.value = true
		pageNo = if (isRefresh) 0 else {
			pageNo + 1
		}
		retrieveData(block = {
			RequestHolder.getCaseList(
				pageNo = pageNo,
				pageSize = Constants.PAGE_GROUP_SIZE
			)
		}, onSuccess = {
			this@CaseViewModel.pageNo = it?.pageNo?: 0
			val taskList = it?.caseList
			noMoreData.value =
				taskList.isNullOrEmpty() || taskList.size < Constants.PAGE_GROUP_SIZE

			val newList = NewCaseGroupListItem.makeCaseGroupList(taskList)
			val currentList = caseList.value
			caseList.value = if(isRefresh){
				Logger.d("getCaseList", "new size ${newList.size}")
				newList
			} else {
				val finalList = mutableListOf<NewCaseListItem>()
				finalList.apply {
					addAll(currentList?.toMutableList()?: mutableListOf())
					addAll(newList)
				}

				Logger.d("getCaseList", "final size ${finalList?.size}")
				finalList
			}
		}, onError = {
			caseList.value = mutableListOf()
			responseStateLiveData.value = ResponseState.TYPE_UNKNOWN
			false
		}, onComplete = {
			isRefreshing.value = false
			isRequestNow = false
			false
		})
	}

	companion object {
		val TAG = "CaseViewModel"
	}
}

class CaseListSource : PagingSource<Int, NewCaseListItem>() {
	override fun getRefreshKey(state: PagingState<Int, NewCaseListItem>): Int? {
		return null
	}

	override suspend fun load(params: LoadParams<Int>): LoadResult<Int, NewCaseListItem> {
		return try {
			val currentPage = params.key ?: 1
			val pageSize = params.loadSize
			Log.d(TAG, "currentPage: $currentPage")
			Log.d(TAG, "pageSize: $pageSize")

			val responseResult = RequestHolder.getCaseList(currentPage, pageSize)
			val caseList = NewCaseGroupListItem.makeCaseGroupList(responseResult.getResponseData()?.caseList)

			// 加载分页
			val everyPageSize = 4
			val initPageSize = 8
			val preKey = if (currentPage == 1) null else currentPage.minus(1)
			var nextKey: Int? = if (currentPage == 1) {
				initPageSize / everyPageSize
			} else {
				currentPage.plus(1)
			}
			Log.d(TAG, "currentPage: $currentPage")
			Log.d(TAG, "preKey: $preKey")
			Log.d(TAG, "nextKey: $nextKey")
			if (caseList.isEmpty()) {
				nextKey = null
			}
			Log.d(TAG, "final nextKey: $nextKey")

			LoadResult.Page(
				data = caseList,
				prevKey = preKey,
				nextKey = nextKey
			)
		} catch (e: Exception) {
			LoadResult.Error(e)
		}
	}

}