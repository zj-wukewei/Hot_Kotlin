package com.wkw.hot.domain.repository

import com.wkw.hot.domain.model.PagePopularEntity
import io.reactivex.Observable

/**
 * Created by hzwukewei on 2017-6-6.
 */
interface HotRepository {
    fun getPopularList(page: Int, word: String?): Observable<PagePopularEntity>
}