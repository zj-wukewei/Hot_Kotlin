package com.wkw.hot.view.base

/**
 * Created by hzwukewei on 2017-6-8.
 */
interface LoadDataView : MvpView {

    fun loading()

    fun showError(e: Exception)

    fun loadFinsh()

}