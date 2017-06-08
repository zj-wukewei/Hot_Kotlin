package com.wkw.hot.util

import android.content.Context
import android.support.v4.content.ContextCompat
import android.widget.Toast

/**
 * Created by hzwukewei on 2017-6-7.
 */

fun Context.showToast(msg: String) {
    Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
}

fun Context.color(res: Int): Int = ContextCompat.getColor(this, res)