package com.wkw.hot.domain.model

import com.google.gson.annotations.SerializedName


/**
 * Created by hzwukewei on 2017-6-6.
 */
class PopularEntity(
        @SerializedName("date")
        var ctime: String?,
        @SerializedName("title")
        var title: String?,
        @SerializedName("userName")
        var description: String,
        @SerializedName("contentImg")
        var picUrl: String?,
        @SerializedName("url")
        var url: String
)