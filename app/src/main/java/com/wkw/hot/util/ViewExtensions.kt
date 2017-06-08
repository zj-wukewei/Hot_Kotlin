package com.wkw.hot.util

import android.content.Context
import android.view.View
import android.widget.TextView

/**
 * Created by hzwukewei on 2017-6-7.
 */
fun View.singeleClick(l: (android.view.View?) -> Unit) {
    setOnClickListener(SingleClickListener(l))
}

val View.ctx: Context
    get() = context

var TextView.textColor: Int
    get() = currentTextColor
    set(v) = setTextColor(v)

fun View.slideExit() {
    if (translationY == 0f) animate().translationY(-height.toFloat())
}

fun View.slideEnter() {
    if (translationY < 0f) animate().translationY(0f)
}