package com.wkw.hot.view.base

import android.os.Bundle
import android.view.View

/**
 * Created by wukewei on 17/6/8.
 */
abstract class LazyFragment : ListBaseFragment() {

    protected var isViewInitiated: Boolean = false
    protected var isVisibleToUser: Boolean = false
    protected var isDataInitiated: Boolean = false

    protected var mFragmentView: View? = null

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        isViewInitiated = true
        prepareFetchData()
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        this.isVisibleToUser = isVisibleToUser
        prepareFetchData()
    }

    private fun prepareFetchData(): Boolean {
        return prepareFetchData(false)
    }

    /**
     * 准备去取数据
     * @param forceUpdate 是否强制刷新数据
     * *
     * @return
     */
    fun prepareFetchData(forceUpdate: Boolean): Boolean {
        if (isVisibleToUser && isViewInitiated && (!isDataInitiated || forceUpdate)) {
            fetchData(true)
            isDataInitiated = true
            return true
        }
        return false
    }

}