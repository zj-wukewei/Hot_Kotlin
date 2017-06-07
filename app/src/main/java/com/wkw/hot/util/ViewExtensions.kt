package com.wkw.hot.util

import android.view.View

/**
 * Created by hzwukewei on 2017-6-7.
 */
fun View.singeleClick(l: (android.view.View?) -> Unit) {
    setOnClickListener(SingleClickListener(l))
}