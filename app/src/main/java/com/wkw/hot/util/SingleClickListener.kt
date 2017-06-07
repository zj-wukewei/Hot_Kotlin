package com.wkw.hot.util

import android.view.View
import android.view.ViewConfiguration

/**
 * Created by hzwukewei on 2017-6-7.
 */
class SingleClickListener(val click: (v: View) -> Unit) : View.OnClickListener {


    companion object {
        private val DOUBLE_CLICK_TIMEOUT = ViewConfiguration.getDoubleTapTimeout()
    }

    private var lastClick: Long = 0

    override fun onClick(v: View) {
        if (getLastClickTimeout() > DOUBLE_CLICK_TIMEOUT) {
            lastClick = System.currentTimeMillis()
            click(v)
        }
    }

    private fun getLastClickTimeout(): Long {
        return System.currentTimeMillis() - lastClick
    }
}