package com.wkw.hot.data.repository

import com.wkw.hot.data.api.HotApi
import com.wkw.hot.domain.DomainConstanst
import com.wkw.hot.domain.model.PagePopularEntity
import com.wkw.hot.domain.repository.HotRepository
import io.reactivex.Observable
import javax.inject.Inject

/**
 * Created by hzwukewei on 2017-6-6.
 */
class HotDataRepository @Inject constructor(private val hotApi: HotApi): HotRepository {

    override fun getPopularList(page: Int, word: String?): Observable<PagePopularEntity> {
        return hotApi.getPopular(page, word, DomainConstanst.Showapi_appid, DomainConstanst.Api_Key)
                .compose(RepositoryUtils.handleResult())
    }

}