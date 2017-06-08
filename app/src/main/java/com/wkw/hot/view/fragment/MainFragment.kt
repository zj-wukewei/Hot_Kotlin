package com.wkw.hot.view.fragment

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.wkw.hot.HotApp
import com.wkw.hot.R
import com.wkw.hot.internal.di.subcomponents.main.MainModule
import com.wkw.hot.model.PopularModel
import com.wkw.hot.view.contract.MainContract
import com.wkw.hot.view.presenter.MainPresenter
import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.android.synthetic.main.view_loading.*
import kotlinx.android.synthetic.main.view_retry.*
import javax.inject.Inject

/**
 * Created by hzwukewei on 2017-6-8.
 */
class MainFragment : Fragment(), MainContract.MainView {

    companion object {
        val TYPE: String = "TYPE"
        fun newInstance(type: String): MainFragment {
            val fragment = MainFragment()
            val bundle = Bundle()
            bundle.putString(TYPE, type)
            fragment.arguments = bundle
            return fragment
        }
    }

    @Inject
    lateinit var presenter: MainPresenter

    var page: Int = 0
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        HotApp.graph.plus(MainModule(this))
                .injectTo(this)
        val type = arguments?.getString(TYPE)
        type?.let {
            presenter.getPoplars(page, type)
        }
    }

    override fun showPoplars(populars: List<PopularModel>?) {

        populars?.let {
            val adapter = MainAdapter {

            }
            adapter.populars = populars
            recycler_view.adapter = adapter
            recycler_view.layoutManager = LinearLayoutManager(activity)
            adapter.notifyDataSetChanged()
        }
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
        return activity.applicationContext
    }


    override fun showLoading() {
        rl_loading.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        rl_loading.visibility = View.GONE
    }

    override fun showRetry() {
        rl_retry.visibility = View.VISIBLE
    }

    override fun hideRetry() {
        rl_retry.visibility = View.GONE
    }

}