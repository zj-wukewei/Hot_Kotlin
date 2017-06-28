package com.wkw.hot.view.fragment

import android.view.View
import com.wkw.hot.R
import com.wkw.hot.model.PopularModel
import com.wkw.hot.util.loadUrl
import com.wkw.hot.util.singleClick
import com.wkw.hot.view.adapter.BaseAdapter
import kotlinx.android.synthetic.main.item_main.view.*

/**
 * Created by hzwukewei on 2017-6-8.
 */
class MainAdapter(val listener: (PopularModel) -> Unit) : BaseAdapter<PopularModel>() {

    override fun bind(itemView: View, item: PopularModel) {
        with(item) {
            itemView.img_item.loadUrl("$picUrl")
            itemView.tv_title.text = title
            itemView.tv_description.text = "来自:${description}"
        }
        itemView.singleClick { listener(item) }
    }

    override fun getLayoutId(): Int {
        return R.layout.item_main
    }

    override fun areItemsTheSame(oldItem: PopularModel, newItem: PopularModel): Boolean {
        return oldItem.title == newItem.title
    }

    override fun areContentsTheSame(oldItem: PopularModel, newItem: PopularModel): Boolean {
        return oldItem == newItem
    }


}