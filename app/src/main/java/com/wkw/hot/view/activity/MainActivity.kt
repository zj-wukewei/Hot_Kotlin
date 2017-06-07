package com.wkw.hot.view.activity

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.wkw.hot.HotApp
import com.wkw.hot.R
import com.wkw.hot.internal.di.subcomponents.main.MainModule
import com.wkw.hot.model.PopularModel
import com.wkw.hot.view.contract.MainContract
import com.wkw.hot.view.presenter.MainPresenter
import javax.inject.Inject

class MainActivity : AppCompatActivity(), MainContract.MainView {


    @Inject
    lateinit var presenter: MainPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        HotApp.graph.plus(MainModule(this))
                .injectTo(this)
        presenter.getPoplars(1, "你好")
    }

    override fun showPoplars(populars: List<PopularModel>) {
        Log.d("main", populars.size.toString())
    }

    override fun onResume() {
        super.onResume()
        presenter.resume()
    }

    override fun onPause() {
        super.onPause()
        presenter.pause()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.destroy()
    }

    override fun context(): Context {
        return this
    }


}
