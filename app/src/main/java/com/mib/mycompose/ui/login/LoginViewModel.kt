package com.mib.mycompose.ui.login

import androidx.lifecycle.MutableLiveData
import com.mib.mycompose.manager.UserInfoManager
import com.mib.mycompose.model.LoginData
import com.mib.mycompose.net.ApiException
import com.mib.mycompose.net.RequestHolder
import com.mib.mycompose.net.ResponseState
import com.mib.mycompose.viewmodel.BaseViewModel

/**
 *  author : cengyimou
 *  date : 2023/11/30 15:24
 *  description :
 */
class LoginViewModel : BaseViewModel(){

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
				throwableLiveData.value = it
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


}