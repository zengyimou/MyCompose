package com.mib.mycompose.base

import android.os.Bundle
import androidx.activity.ComponentActivity
import com.mib.mycompose.ext.toast
import com.mib.mycompose.viewmodel.BaseViewModel

/**
 *  author : cengyimou
 *  date : 2024/4/9 16:40
 *  description :
 */
abstract class BaseActivity: ComponentActivity() {

	lateinit var baseViewModel: BaseViewModel

	abstract fun createViewModel(): BaseViewModel

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		baseViewModel = createViewModel()
		baseViewModel.throwableLiveData.observe(this){
			toast(it.message?: "")
		}
	}
}