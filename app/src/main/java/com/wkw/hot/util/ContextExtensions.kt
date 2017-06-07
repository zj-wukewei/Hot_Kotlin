package com.wkw.hot.util

import android.content.Context
import android.widget.Toast

/**
 * Created by hzwukewei on 2017-6-7.
 */

inline fun Context.showToast(msg: String) {
    Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
}