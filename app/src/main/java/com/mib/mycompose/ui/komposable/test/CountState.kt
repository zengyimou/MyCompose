package com.mib.mycompose.ui.komposable.test

/**
 *  author : cengyimou
 *  date : 2025/8/9 11:06
 *  description :
 */
data class CountState(var count: Int = 0)

sealed interface CounterAction {
    data class Increment(val value: Int = 1) : CounterAction
    data class Decrement(val value: Int = 1) : CounterAction
    object Reset : CounterAction
}
