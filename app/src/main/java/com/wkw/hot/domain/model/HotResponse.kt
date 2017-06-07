package com.wkw.hot.domain.model

/**
 * Created by hzwukewei on 2017-6-6.
 */
class HotResponse<out T>(val showapi_res_code: Int,
                         val showapi_res_error: String,
                         val showapi_res_body: T) {
    fun isSuccess(): Boolean {
        return showapi_res_code == 0
    }
}