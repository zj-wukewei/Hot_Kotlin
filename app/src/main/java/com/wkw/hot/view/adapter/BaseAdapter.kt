package com.wkw.hot.view.adapter

import android.annotation.SuppressLint
import android.os.AsyncTask
import android.support.annotation.MainThread
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/**
 * Created by hzwukewei on 2017-6-20.
 */
abstract class BaseAdapter<T> : RecyclerView.Adapter<BaseAdapter.BaseViewHolder>() {
    var items: List<T>? = null
    private var dataVersion = 0


    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): BaseViewHolder {
        return BaseViewHolder(LayoutInflater.from(parent?.context).inflate(getLayoutId(), parent, false))
    }

    override fun getItemCount(): Int {
        return items?.size ?: 0
    }


    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        items?.let {
            val item = it[position]
            bind(holder.itemView, item)
        }
    }

    @SuppressLint("StaticFieldLeak")
    @MainThread
    fun replace(update: List<T>?) {
        dataVersion++
        if (items == null) {
            if (update == null) {
                return
            }
            items = update
            notifyDataSetChanged()
        } else if (update == null) {
            val oldSize = items!!.size
            items = null
            notifyItemRangeChanged(0, oldSize)
        } else {
            val startVersion = dataVersion
            val oldItems = items
            object : AsyncTask<Void, Void, DiffUtil.DiffResult>() {
                override fun doInBackground(vararg voids: Void): DiffUtil.DiffResult {
                    return DiffUtil.calculateDiff(object : DiffUtil.Callback() {
                        override fun getOldListSize(): Int {
                            return oldItems!!.size
                        }

                        override fun getNewListSize(): Int {
                            return update.size
                        }

                        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                            val oldItem = oldItems!![oldItemPosition]
                            val newItem = update[newItemPosition]
                            return areItemsTheSame(oldItem, newItem)
                        }

                        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                            val oldItem = oldItems!![oldItemPosition]
                            val newItem = update[newItemPosition]
                            return areContentsTheSame(oldItem, newItem)
                        }
                    })
                }

                override fun onPostExecute(diffResult: DiffUtil.DiffResult) {
                    if (startVersion != dataVersion) {
                        // ignore update
                        return
                    }
                    Log.d("diffResult", update.size.toString())
                    items = update
                    diffResult.dispatchUpdatesTo(this@BaseAdapter)

                }
            }.execute()
        }
    }

    class BaseViewHolder(item: View) : RecyclerView.ViewHolder(item)

    abstract fun bind(itemView: View, item: T)

    abstract fun getLayoutId(): Int

    abstract fun areItemsTheSame(oldItem: T, newItem: T): Boolean

    abstract fun areContentsTheSame(oldItem: T, newItem: T): Boolean

}