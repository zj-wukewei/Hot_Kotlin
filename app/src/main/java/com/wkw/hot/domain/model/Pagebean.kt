package com.wkw.hot.domain.model

import com.google.gson.annotations.SerializedName

/**
 * Created by hzwukewei on 2017-6-6.
 */
class Pagebean(
        @SerializedName("allPages")
        var allPages: Int,
        @SerializedName("contentlist")
        var contentlist: List<PopularEntity>,
        @SerializedName("currentPage")
        var currentPage: Int,
        @SerializedName("allNum")
        var allNum: Int,
        @SerializedName("maxResult")
        var maxResult: Int
)