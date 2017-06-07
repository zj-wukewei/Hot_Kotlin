package com.wkw.hot.data.exception

/**
 * Created by hzwukewei on 2017-6-6.
 */
class ResponseException(val code: Int, msg: String) : Exception(msg)