package com.wkw.hot.view.fragment

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.wkw.hot.R
import com.wkw.hot.model.PopularModel
import com.wkw.hot.util.loadUrl
import com.wkw.hot.util.singeleClick
import kotlinx.android.synthetic.main.item_main.view.*

/**
 * Created by hzwukewei on 2017-6-8.
 */
class MainAdapter(val populars: List<PopularModel>, val listener: (PopularModel) -> Unit) : RecyclerView.Adapter<MainAdapter.ViewHolder>() {


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(populars[position])
        holder.itemView.singeleClick { listener(populars[position]) }
    }

    override fun getItemCount(): Int {
        return populars.size
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent?.context).inflate(R.layout.item_main, parent, false))
    }


    inner class ViewHolder(item: View) : RecyclerView.ViewHolder(item) {

        fun bind(model: PopularModel) {
            with(model) {
                itemView.img_item.loadUrl("$picUrl")
                itemView.tv_title.text = "$title"
                itemView.tv_description.text = "来自:${description}"
            }
        }
    }
}