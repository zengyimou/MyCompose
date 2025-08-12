package com.mib.mycompose.ui.komposable.test

import com.toggl.komposable.architecture.ReduceResult
import com.toggl.komposable.architecture.Reducer
import com.toggl.komposable.extensions.withEffect
import com.toggl.komposable.extensions.withoutEffect

/**
 *  author : cengyimou
 *  date : 2025/8/9 11:08
 *  description :
 */

class CountReducer: Reducer<CountState, CounterAction> {
    override fun reduce(
        state: CountState,
        action: CounterAction
    ): ReduceResult<CountState, CounterAction> {
        return when (action) {
            is CounterAction.Increment -> state.copy(count = state.count + action.value).withoutEffect()
            is CounterAction.Decrement -> state.copy(count = state.count - action.value).withoutEffect()
            CounterAction.Reset -> state.copy(count = 0).withEffect()

        }
    }

}