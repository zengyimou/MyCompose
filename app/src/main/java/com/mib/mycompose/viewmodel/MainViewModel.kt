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

	var nav: NavHostController? = null

	fun login(account: String, password: String){
		retrieveData(
			block = {
				RequestHolder.login(account, password)
			},
			onSuccess = {data ->
				data?.let { UserInfoManager.setLoginInfo(it) }
				nav?.navigate(NavScreen.Main.route)
			},
			onError = {
				false
			}
		)
	}


}