package com.wkw.hot.view.fragment

import android.content.Context
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.wkw.hot.HotApp
import com.wkw.hot.R
import com.wkw.hot.domain.DomainConstanst
import com.wkw.hot.internal.di.subcomponents.main.MainModule
import com.wkw.hot.model.PopularModel
import com.wkw.hot.util.startActivity
import com.wkw.hot.view.activity.WebActivity
import com.wkw.hot.view.base.HotLazyFragment
import com.wkw.hot.view.contract.MainContract
import com.wkw.hot.view.presenter.MainPresenter
import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.android.synthetic.main.view_loading.*
import kotlinx.android.synthetic.main.view_retry.*
import javax.inject.Inject


/**
 * Created by hzwukewei on 2017-6-8.
 */
class MainFragment : HotLazyFragment(), MainContract.MainView {

    private val TAG: String = MainFragment::class.java.simpleName

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

    private var page: Int = 0
    private var hasMore: Boolean = true
    private lateinit var mAdapter: MainAdapter
    private var mList = ArrayList<PopularModel>()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        Log.d(TAG, "onCreateView")
        if (mFragmentView != null) {
            (mFragmentView?.parent as? ViewGroup)?.removeView(mFragmentView)
        } else {
            mFragmentView = inflater.inflate(R.layout.fragment_main, container, false)
        }
        return mFragmentView
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        HotApp.graph.plus(MainModule(this))
                .injectTo(this)
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        mAdapter = MainAdapter(mList) {
            context.startActivity<WebActivity>(WebActivity.TITLE to it.title, WebActivity.URL to it.url)
        }

        recycler_view.adapter = mAdapter
        recycler_view.layoutManager = LinearLayoutManager(activity)
        recycler_view.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (!recyclerView?.canScrollVertically(1)!!) {
                    ++page
                    fetchData()
                }
            }
        })
        bt_retry.setOnClickListener {
            fetchData()
        }
    }

    override fun fetchData() {
        val type = arguments?.getString(TYPE)
        type?.let {
            if (hasMore) {
                presenter.getPoplars(page, type)
            }
        }

    }

    override fun showPoplars(populars: List<PopularModel>?) {
        populars?.let {
            hasMore = (populars.size >= DomainConstanst.PAGE_SIZE)
            mList.addAll(populars)
            mAdapter.notifyDataSetChanged()
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
        if (page == 0) {
            rl_loading.visibility = View.VISIBLE
        }
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