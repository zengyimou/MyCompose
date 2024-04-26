package com.mib.mycompose.ui.main

import android.content.Context
import androidx.annotation.Keep
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.mib.mycompose.ext.toast
import com.mib.mycompose.model.MainPageData
import com.mib.mycompose.model.PerformanceData
import com.mib.mycompose.net.RequestHolder
import com.mib.mycompose.util.Logger
import com.mib.mycompose.viewmodel.BaseViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 *  author : cengyimou
 *  date : 2024/4/11 11:30
 *  description :
 */
class MainPageViewModel: BaseViewModel(){

	var caseDataList = MutableLiveData<List<CaseData>>()

	fun initCaseDataList(){
		val list1 = mutableListOf<CaseData>()
		list1.add(CaseData("A123456789", "123456789", 0, "A123456789"))
		list1.add(CaseData("B123456789", "123456789", 1, "B123456789"))
		caseDataList.value = list1
	}

	fun clickMainPageTab(index: Int) {
		val list1 = mutableListOf<CaseData>()
		val list2 = mutableListOf<CaseData>()
		if(index == 0){
			list1.add(CaseData("A123456789", "123456789", 0, "A123456789"))
			list1.add(CaseData("B123456789", "123456789", 1, "B123456789"))
			caseDataList.value = list1
		}else{
			list2.add(CaseData("C123456789", "123456789", 1, "C123456789"))
			list2.add(CaseData("D123456789", "123456789", 2, "D123456789"))
			caseDataList.value = list2
		}
	}

	val performanceDataLiveData = MutableLiveData<PerformanceData?>()
	val caseStaticDataLiveData = MutableLiveData<MainPageData?>()
	fun getMainPageData(context: Context) {
		viewModelScope.launch(CoroutineExceptionHandler { _, throwable ->
			//请求过程失败
			context.toast(throwable.message?: "")
		}) {
			val performanceInfoResponse = RequestHolder.getPerformanceInfo()
			val caseStaticResponse = RequestHolder.getCaseStatistic()
			Logger.d("getMainPageData", "performanceInfoResponse: ${performanceInfoResponse.isSuccessful()}")
			Logger.d("getMainPageData", "caseStaticResponse: ${caseStaticResponse.isSuccessful()}")
			if(performanceInfoResponse.isSuccessful()) {
				performanceDataLiveData.value = performanceInfoResponse.data
			}
			if(caseStaticResponse.isSuccessful()){
				caseStaticDataLiveData.value = caseStaticResponse.data
			}

		}
	}



}

@Keep
data class CaseData(
	var caseNum: String? = null,
	var amount: String? = null,
	var eventType: Int? = 0,
	var eventTitle: String? = null
)