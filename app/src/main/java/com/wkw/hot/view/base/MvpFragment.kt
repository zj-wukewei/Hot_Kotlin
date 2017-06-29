package com.wkw.hot.view.base

import android.content.Context

/**
 * Created by hzwukewei on 2017-6-29.
 */
abstract class MvpFragment<V : MvpView, out P : MvpPresenter<V>> : BaseFragment(), MvpView {

    override fun onResume() {
        super.onResume()
        getPresenter().resume()
    }

    override fun onPause() {
        super.onPause()
        getPresenter().pause()
    }

    override fun onDestroy() {
        super.onDestroy()
        getPresenter().destroy()
    }

    override fun context(): Context {
        return activity.applicationContext
    }

    abstract fun getPresenter(): P
}