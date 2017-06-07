package com.wkw.hot.data.exception

import com.wkw.hot.domain.model.HotResponse

/**
 * Created by hzwukewei on 2017-6-6.
 */
class ResponseException : Exception {
    constructor(hotResponse: HotResponse<*>) : super(hotResponse.showapi_res_error)
}