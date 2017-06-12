package com.wkw.hot.util

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager

/**
 * Created by hzwukewei on 2017-6-12.
 */
class LoadMoreDelegate(private val loadMoreSubject: LoadMoreSubject) {

    fun attach(recyclerView: RecyclerView) {
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == RecyclerView.SCROLL_STATE_IDLE && !loadMoreSubject.isLoading()) {
                    val layoutManager: RecyclerView.LayoutManager = recyclerView!!.layoutManager
                    if (layoutManager is LinearLayoutManager) {
                        val lastVisiblePosition = layoutManager.findLastVisibleItemPosition()
                        if (lastVisiblePosition >= recyclerView.adapter.itemCount - 1) {
                            loadMoreSubject.onLoadMore()
                        }
                    } else if (layoutManager is StaggeredGridLayoutManager) {
                        val last = IntArray(layoutManager.spanCount)
                        layoutManager.findLastCompletelyVisibleItemPositions(last)
                        last.filter { it >= recyclerView.adapter.itemCount - 1 }
                                .forEach { loadMoreSubject.onLoadMore() }
                    }
                }
            }
        })
    }


     interface LoadMoreSubject {
        fun isLoading(): Boolean
        fun onLoadMore()
    }
}