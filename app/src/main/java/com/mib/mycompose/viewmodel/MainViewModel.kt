package com.mib.mycompose.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavHostController
import com.mib.mycompose.constants.C
import com.mib.mycompose.manager.UserInfoManager
import com.mib.mycompose.model.LoginData
import com.mib.mycompose.net.RequestHolder
import com.mib.mycompose.net.ResponseState
import com.mib.mycompose.net.ResultResponse
import com.mib.mycompose.ui.widget.NavScreen
import com.mib.mycompose.util.Logger

/**
 *  author : cengyimou
 *  date : 2023/11/30 15:24
 *  description :
 */
class MainViewModel : BaseViewModel(){


	val resetBottomBarSelectIndexLiveData = MutableLiveData<Boolean>()
	fun resetBottomBarSelectIndex(enable: Boolean){
		resetBottomBarSelectIndexLiveData.value = enable
	}

}