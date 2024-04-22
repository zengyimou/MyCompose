package com.mib.mycompose.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavHostController
import com.mib.mycompose.manager.UserInfoManager
import com.mib.mycompose.net.RequestHolder
import com.mib.mycompose.ui.widget.NavScreen

/**
 *  author : cengyimou
 *  date : 2023/11/30 15:24
 *  description :
 */
class MainViewModel : BaseViewModel(){

	val loginLiveData = MutableLiveData<Boolean>()


	fun login(account: String, password: String){
		retrieveData(
			block = {
				RequestHolder.login(account, password)
			},
			onSuccess = {data ->
				data?.let { UserInfoManager.setLoginInfo(it) }
				loginLiveData.value = true
			},
			onError = {
				loginLiveData.value = false
				false
			}
		)
	}

	val resetBottomBarSelectIndexLiveData = MutableLiveData<Boolean>()
	fun resetBottomBarSelectIndex(enable: Boolean){
		resetBottomBarSelectIndexLiveData.value = enable
	}

}