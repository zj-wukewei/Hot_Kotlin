package com.wkw.hot.exception

import android.content.ContentValues.TAG
import android.content.Context
import android.net.ConnectivityManager
import android.text.TextUtils
import android.util.Log
import com.wkw.hot.BuildConfig
import com.wkw.hot.R
import com.wkw.hot.data.exception.NetworkConnectionException
import com.wkw.hot.data.exception.ResponseException


/**
 * Created by hzwukewei on 2017-6-7.
 */

fun Context.createException(exception: Exception): String {
    if (!TextUtils.isEmpty(exception.message)) {
        Log.e(TAG, exception.message)
    }

    var message = this.getString(R.string.exception_message_generic)

    if (exception is NetworkConnectionException || !this.isThereInternetConnection()) {
        message = this.getString(R.string.exception_message_no_connection)
    } else if (exception is ResponseException) {
        message = exception.message
    } else if (BuildConfig.DEBUG) {
        message = exception.message
    }

    return message
}

fun Context.isThereInternetConnection(): Boolean {
    val connectivityManager: ConnectivityManager = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    return connectivityManager.activeNetworkInfo?.isConnectedOrConnecting ?: false
}
