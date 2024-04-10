package com.mib.mycompose.viewmodel

import androidx.lifecycle.MutableLiveData
import com.mib.mycompose.manager.UserInfoManager
import com.mib.mycompose.net.RequestHolder

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

	var loginListener: LoginPageListener? = null
		private set

	fun setLoginPageListener(loginPageListener: LoginPageListener){
		loginListener = loginPageListener
	}

	/**
	 * 监听登录页交互
	 */
	interface LoginPageListener {
		fun login(listId: String, password: String)
	}

}