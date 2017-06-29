package com.wkw.hot.view.base

import android.content.Context
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.wkw.hot.domain.DomainConstanst
import com.wkw.hot.exception.createException
import com.wkw.hot.exception.isThereInternetConnection
import com.wkw.hot.util.LoadMoreDelegate
import com.wkw.hot.util.showToast
import com.wkw.hot.view.adapter.LoadMoreAdapter
import com.wkw.hot.view.widget.ProgressLayout

/**
 * Created by hzwukewei on 2017-6-12.
 */
abstract class ListBaseFragment<V : MvpView, out P : MvpPresenter<V>> : MvpFragment<V, P>(), LoadMoreDelegate.LoadMoreSubject, LoadDataView {

    protected var mIsFetching: Boolean = false
    protected var mCurrentPage: Int = DomainConstanst.FIRST_PAGE
    protected var hasMore: Boolean = true
        set(value) {
            field = value
            mLoadMoreAdapter.itemType = if (value) LoadMoreAdapter.ITEM_LOADING else LoadMoreAdapter.ITEM_LOADING_DONE
        }
    private lateinit var loadMoreDelegate: LoadMoreDelegate
    private var isInit = true
    private var isFist = true
    private lateinit var mLoadMoreAdapter: LoadMoreAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadMoreDelegate = LoadMoreDelegate(this)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (isFist) {
            isFist = false
            initSwipeRefreshAndRecycler()
        }
    }

    protected fun initSwipeRefreshAndRecycler() {
        getSwipeRefreshLayout().setColorSchemeResources(android.R.color.black)
        getSwipeRefreshLayout().setOnRefreshListener {
            mCurrentPage = DomainConstanst.FIRST_PAGE
            hasMore = true
            fetchData(true)
        }
        mLoadMoreAdapter = LoadMoreAdapter(getAdapter(), {
            fetchData(false)
        })
        getRecyclerView().adapter = mLoadMoreAdapter
        getRecyclerView().layoutManager = getDefaultLayoutManager()
        loadMoreDelegate.attach(getRecyclerView())
    }

    override fun onLoadMore() {
        if (hasMore) {
            mCurrentPage++
            fetchData(false)
        }
    }

    override fun isLoading(): Boolean {
        return mIsFetching
    }

    override fun loading() {
        mIsFetching = true
        if (mCurrentPage == DomainConstanst.FIRST_PAGE && isInit) {
            getProgressLayout().showLoading()
        }
        isInit = false
    }

    protected fun isLoadMore(): Boolean {
        return mCurrentPage > DomainConstanst.FIRST_PAGE
    }

    override fun loadFinish() {
        mIsFetching = false
        if (mCurrentPage == DomainConstanst.FIRST_PAGE) {
            setRefreshing(false)
        }
        val adapter = getRecyclerView().adapter
        if (mCurrentPage == DomainConstanst.FIRST_PAGE && adapter != null && adapter.itemCount == 0) {
            isInit = true
            loadEmpty()
            return
        }
        getProgressLayout().showContent()
        if (mCurrentPage == DomainConstanst.FIRST_PAGE) {
            setRefreshing(false)
        }
        mIsFetching = false
    }

    private fun loadEmpty() {
        getProgressLayout().showNone(View.OnClickListener {
            fetchData(true)
        })
        if (mCurrentPage == DomainConstanst.FIRST_PAGE) {
            setRefreshing(false)
        }
        mIsFetching = false
    }

    fun setFailedText(text: CharSequence) {
        getProgressLayout().setFailedText(text)
    }

    protected fun getDefaultLayoutManager(): RecyclerView.LayoutManager {
        return LinearLayoutManager(activity)
    }

    override fun showError(e: Exception) {
        mIsFetching = false
        val msg = context.createException(e)
        if (mCurrentPage == DomainConstanst.FIRST_PAGE) {
            isInit = true
            if (context.isThereInternetConnection()) {
                setFailedText(msg)
                getProgressLayout().showFailed(View.OnClickListener {
                    fetchData(true)
                })
            } else {
                getProgressLayout().showNetError(View.OnClickListener {
                    fetchData(true)
                })
            }
        } else {
            mLoadMoreAdapter.itemType = LoadMoreAdapter.ITEM_LOADING_ERROR
            context.showToast(msg)
        }
        setRefreshing(false)
    }

    private fun restoreCurrentPage() {
        if (mCurrentPage > DomainConstanst.FIRST_PAGE) {
            mCurrentPage--
        }
    }

    protected fun setRefreshing(refreshing: Boolean) {
        if (refreshing) {
            getSwipeRefreshLayout().isRefreshing = true
        } else {
            getSwipeRefreshLayout().postDelayed({
                getSwipeRefreshLayout().isRefreshing = false
            }, 800)
        }
    }

    override fun context(): Context {
        return activity.applicationContext
    }

    abstract fun fetchData(clear: Boolean)
    abstract fun getRecyclerView(): RecyclerView
    abstract fun getProgressLayout(): ProgressLayout
    abstract fun getSwipeRefreshLayout(): SwipeRefreshLayout
    abstract fun getAdapter(): RecyclerView.Adapter<RecyclerView.ViewHolder>
}