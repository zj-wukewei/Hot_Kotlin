package com.wkw.hot.domain.exception

/**
 * Created by hzwukewei on 2017-6-6.
 */
public class DefaultErrorBundle(exception: Exception?) : ErrorBundle {

    var mException: Exception? = exception

    val DEFAULT_ERROR_MSG: String = "Unknown error"

    override fun getException(): Exception? {
        return mException
    }

    override fun getErrorMessage(): String {
        return mException?.message ?: DEFAULT_ERROR_MSG
    }

}