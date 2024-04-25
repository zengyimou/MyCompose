package com.mib.mycompose.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavHostController
import com.mib.mycompose.manager.UserInfoManager
import com.mib.mycompose.model.LoginData
import com.mib.mycompose.net.RequestHolder
import com.mib.mycompose.net.ResponseState
import com.mib.mycompose.net.ResultResponse
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
				data?.let {
					data.listId = account
					UserInfoManager.setLoginInfo(data)
					getUserInfo(data)
				}
				if(data == null) loginLiveData.value = false
			},
			onError = {
				loginLiveData.value = false
				false
			}
		)
	}

	private fun getUserInfo(loginData: LoginData) {
		retrieveData({
			RequestHolder.getEmployeeInfo()
		}, { data ->
			if (data != null) {
				loginData.listId = data.listId
				loginData.listName = data.listName
				loginData.listMobile = data.listMobile
				loginData.roleId = data.roleId
//				UserInfoManager.setLoginInfo(loginData)
				UserInfoManager.reSave()
				loginLiveData.value = true
			}else{
				loginLiveData.value = false
				UserInfoManager.logout()
			}
		}, { throwable ->
			throwableLiveData.value = throwable
			false
		}, {
			responseStateLiveData.value = ResponseState.TYPE_UNKNOWN
			false
		})
	}

	val resetBottomBarSelectIndexLiveData = MutableLiveData<Boolean>()
	fun resetBottomBarSelectIndex(enable: Boolean){
		resetBottomBarSelectIndexLiveData.value = enable
	}

}