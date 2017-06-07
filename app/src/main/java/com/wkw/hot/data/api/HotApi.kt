package com.wkw.hot.data.api

import com.wkw.hot.domain.model.HotResponse
import com.wkw.hot.domain.model.PagePopularEntity
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by hzwukewei on 2017-6-6.
 */
interface HotApi {
    @GET("582-2")
    fun getPopular(@Query("page") page: Int, @Query("key") word: String?,
                   @Query("showapi_appid") appid: String,
                   @Query("showapi_sign") sign: String): Observable<HotResponse<PagePopularEntity>>
}