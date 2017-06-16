package com.wkw.hot.view.fragment

import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
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
import com.wkw.hot.view.base.LazyFragment
import com.wkw.hot.view.contract.MainContract
import com.wkw.hot.view.presenter.MainPresenter
import com.wkw.hot.view.widget.ProgressLayout
import kotlinx.android.synthetic.main.fragment_main.*
import javax.inject.Inject


/**
 * Created by hzwukewei on 2017-6-8.
 */
class MainFragment : LazyFragment(), MainContract.MainView {


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
        getRecyclerView().layoutManager = LinearLayoutManager(activity)
    }

    override fun showPoplars(populars: List<PopularModel>?) {
        populars?.let {
            hasMore = (populars.size >= DomainConstanst.PAGE_SIZE)
            if (!isLoadMore()) {
                mList.clear()
            }
            mList.addAll(populars)
            mAdapter.populars = mList
        }
    }


    override fun getAdapter(): RecyclerView.Adapter<RecyclerView.ViewHolder> {
        mAdapter = MainAdapter {
            context.startActivity<WebActivity>(WebActivity.TITLE to it.title, WebActivity.URL to it.url)
        }
        mAdapter.populars = mList
        return mAdapter as RecyclerView.Adapter<RecyclerView.ViewHolder>
    }

    override fun fetchData(clear: Boolean) {
        val type = arguments?.getString(TYPE)
        type?.let {
            presenter.getPoplars(mCurrentPage, type)
        }
    }

    override fun getRecyclerView(): RecyclerView {
        return recycler_view
    }

    override fun getProgressLayout(): ProgressLayout {
        return progress_layout
    }

    override fun getSwipeRefreshLayout(): SwipeRefreshLayout {
        return swipe_layout
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


}