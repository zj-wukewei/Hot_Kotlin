package com.wkw.hot.view.base

/**
 * Created by hzwukewei on 2017-6-7.
 */
interface MvpPresenter<V : MvpView> {

    var mView: V

    fun getView(): V {
        return mView
    }

    fun resume()

    fun pause()

    fun destroy()
}