package com.mib.mycompose.ui.main

import androidx.annotation.Keep
import androidx.lifecycle.MutableLiveData
import com.mib.mycompose.viewmodel.BaseViewModel

/**
 *  author : cengyimou
 *  date : 2024/4/11 11:30
 *  description :
 */
class MainPageViewModel: BaseViewModel(){

	var caseDataList = MutableLiveData<List<CaseData>>()

	fun clickMainPageTab(index: Int) {
		val list1 = mutableListOf<CaseData>()
		val list2 = mutableListOf<CaseData>()
		if(index == 0){
			list1.add(CaseData("A123456789", "123456789", 0, "A123456789"))
			list1.add(CaseData("B123456789", "123456789", 1, "B123456789"))
			caseDataList.value = list1
		}else{
			list2.add(CaseData("C123456789", "123456789", 0, "C123456789"))
			list2.add(CaseData("D123456789", "123456789", 1, "D123456789"))
			caseDataList.value = list2
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