package com.wkw.hot.view.adapter

import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.item_loadmore.view.*

/**
 * Created by hzwukewei on 2017-6-16.
 */
class LoadMoreAdapter(inner: RecyclerView.Adapter<out RecyclerView.ViewHolder>,
                      val loadError: () -> Unit) : BaseWrapper(inner as RecyclerView.Adapter<RecyclerView.ViewHolder>) {

    companion object {
        //正在加载
        val ITEM_LOADING = -1000
        //加载失败
        val ITEM_LOADING_ERROR = -2000
        //到底了
        val ITEM_LOADING_DONE = -3000
    }

    var itemType = ITEM_LOADING
        set(value) {
            field = value
            notifyItemChanged(itemCount - 1)
        }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ITEM_LOADING -> LoadMoreViewHolder(parent!!)
            ITEM_LOADING_ERROR -> LoadMoreViewHolder(parent!!)
            ITEM_LOADING_DONE -> LoadMoreViewHolder(parent!!)
            else -> innerAdapter.onCreateViewHolder(parent, viewType)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        if (bindViewHolder(position, holder)) {
            return
        }
        innerAdapter.onBindViewHolder(holder, position)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int, payloads: MutableList<Any>?) {
        if (bindViewHolder(position, holder)) {
            return
        }
        innerAdapter.onBindViewHolder(holder, position, payloads)
    }

    private fun bindViewHolder(position: Int, holder: RecyclerView.ViewHolder?): Boolean {
        if (isPositionLoadMore(position)) {
            when (itemType) {
                ITEM_LOADING -> {
                    if (holder!!.itemView.item_loading.visibility != View.VISIBLE) {
                        holder!!.itemView.item_loading.visibility = View.VISIBLE
                        holder.itemView.item_load_end.visibility = View.GONE
                        holder.itemView.item_load_error.visibility = View.GONE
                    }
                }
                ITEM_LOADING_ERROR -> {
                    if (holder!!.itemView.item_load_error.visibility != View.VISIBLE) {
                        holder!!.itemView.item_loading.visibility = View.GONE
                        holder.itemView.item_load_end.visibility = View.GONE
                        holder.itemView.item_load_error.visibility = View.VISIBLE
                        holder.itemView.item_load_error.setOnClickListener {
                            loadError()
                        }
                    }
                }
                ITEM_LOADING_DONE -> {
                    if (holder!!.itemView.item_load_end.visibility != View.VISIBLE) {
                        holder!!.itemView.item_loading.visibility = View.GONE
                        holder.itemView.item_load_end.visibility = View.VISIBLE
                        holder.itemView.item_load_error.visibility = View.GONE
                    }
                }
            }
            return true
        }
        return false
    }

    fun isMoreViewHolder(holder: RecyclerView.ViewHolder?): Boolean {
        if (isPositionLoadMore(holder!!.layoutPosition) || holder is LoadMoreViewHolder) {
            return true
        }
        return false
    }

    override fun getItemCount(): Int {
        return innerAdapter.itemCount + 1
    }

    override fun getItemId(position: Int): Long {
        if (isPositionLoadMore(position)) {
            return itemType.toLong()
        }
        return innerAdapter.getItemId(position)
    }

    override fun getItemViewType(position: Int): Int {
        if (isPositionLoadMore(position)) {
            return itemType
        }
        return innerAdapter.getItemViewType(position)
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView?) {
        val manager = recyclerView!!.layoutManager
        if (manager is GridLayoutManager) {
            manager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    val type = getItemViewType(position)
                    return if (type == ITEM_LOADING ||
                            type == ITEM_LOADING_ERROR ||
                            type == ITEM_LOADING_DONE)
                        manager.spanCount else 1
                }
            }
        }
        innerAdapter.onAttachedToRecyclerView(recyclerView)
    }

    override fun onViewAttachedToWindow(holder: RecyclerView.ViewHolder?) {
        if (isMoreViewHolder(holder)) {
            val lp = holder!!.itemView.layoutParams
            if (lp != null && lp is StaggeredGridLayoutManager.LayoutParams) {
                lp.isFullSpan = true
            }
        } else {
            innerAdapter.onViewAttachedToWindow(holder)
        }
    }

    override fun onViewDetachedFromWindow(holder: RecyclerView.ViewHolder?) {
        if (isMoreViewHolder(holder)) {
            return
        }
        super.onViewDetachedFromWindow(holder)
    }

    override fun onViewRecycled(holder: RecyclerView.ViewHolder?) {
        if (isMoreViewHolder(holder)) {
            return
        }
        super.onViewRecycled(holder)
    }

    fun isPositionLoadMore(position: Int): Boolean {
        return position >= innerAdapter.itemCount
    }

}