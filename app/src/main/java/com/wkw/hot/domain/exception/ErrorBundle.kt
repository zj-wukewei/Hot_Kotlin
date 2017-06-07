package com.wkw.hot.domain.exception

/**
 * Created by hzwukewei on 2017-6-6.
 */
interface ErrorBundle {
    fun getException(): Exception?
    fun getErrorMessage(): String
}