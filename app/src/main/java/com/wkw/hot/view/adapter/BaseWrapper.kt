package com.wkw.hot.view.adapter

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup

/**
 * Created by hzwukewei on 2017-6-16.
 */
open class BaseWrapper(val innerAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup?, position: Int): RecyclerView.ViewHolder {
        return innerAdapter.onCreateViewHolder(parent, position)
    }

    override fun getItemCount(): Int {
        return innerAdapter.itemCount
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        innerAdapter.onBindViewHolder(holder, position)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int, payloads: MutableList<Any>?) {
        innerAdapter.onBindViewHolder(holder, position, payloads)
    }

    override fun onViewAttachedToWindow(holder: RecyclerView.ViewHolder?) {
        innerAdapter.onViewAttachedToWindow(holder)
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView?) {
        innerAdapter.onAttachedToRecyclerView(recyclerView)
    }

    override fun onFailedToRecycleView(holder: RecyclerView.ViewHolder?): Boolean {
        return innerAdapter.onFailedToRecycleView(holder)
    }

    override fun onViewDetachedFromWindow(holder: RecyclerView.ViewHolder?) {
        innerAdapter.onViewDetachedFromWindow(holder)
    }

    override fun setHasStableIds(hasStableIds: Boolean) {
        innerAdapter.setHasStableIds(hasStableIds)
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView?) {
        innerAdapter.onDetachedFromRecyclerView(recyclerView)
    }

    override fun getItemViewType(position: Int): Int {
        return innerAdapter.getItemViewType(position)
    }

    override fun onViewRecycled(holder: RecyclerView.ViewHolder?) {
        innerAdapter.onViewRecycled(holder)
    }

    override fun getItemId(position: Int): Long {
        return innerAdapter.getItemId(position)
    }
}