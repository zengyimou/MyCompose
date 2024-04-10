package com.mib.mycompose.net

/**
 *  author : cengyimou
 *  date : 2024/4/8 19:34
 *  description :
 */
enum class ResponseState {
	/**
	 * 正在刷新
	 */
	TYPE_REFRESHING,

	/**
	 * 刷新成功，有数据
	 */
	TYPE_REFRESHING_SUCCESS,

	/**
	 * 刷新完成，无数据
	 */
	TYPE_EMPTY,

	/**
	 * 正在加载更多
	 */
	TYPE_LOADING,

	/**
	 * 加载更多成功，有数据
	 */
	TYPE_LOADING_SUCCESS,

	/**
	 * 没有更多数据，且隐藏FootView
	 */
	TYPE_NO_MORE_AND_GONE_VIEW,

	/**
	 * 没有更多数据，不隐藏FootView
	 */
	TYPE_NO_MORE,

	/**
	 * 出现错误
	 */
	TYPE_ERROR,

	/**
	 * 出现网络错误
	 */
	TYPE_NET_ERROR,

	/**
	 * 未知
	 */
	TYPE_UNKNOWN,
}