package com.wkw.hot.view.base

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity

/**
 * Created by hzwukewei on 2017-6-7.
 */
abstract class MvpActivity<V : MvpView, out P : MvpPresenter<V>> : AppCompatActivity(), MvpView {

    protected abstract fun getPresenter(): P

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getPresenter().attachView(this as V)
    }

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
        return this
    }
}