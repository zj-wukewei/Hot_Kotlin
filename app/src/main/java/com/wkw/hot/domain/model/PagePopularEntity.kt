package com.wkw.hot.domain.model

import com.google.gson.annotations.SerializedName


/**
 * Created by hzwukewei on 2017-6-6.
 */
class PagePopularEntity(
        @SerializedName("ret_code")
        var retCode: Int,
        @SerializedName("pagebean")
        var pagebean: Pagebean
)