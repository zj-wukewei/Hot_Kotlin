package com.wkw.hot.domain.interactor

import com.wkw.hot.domain.DomainConstanst
import com.wkw.hot.domain.executor.PostExecutionThread
import com.wkw.hot.domain.executor.ThreadExecutor
import com.wkw.hot.domain.model.PagePopularEntity
import com.wkw.hot.domain.repository.HotRepository
import io.reactivex.Observable

/**
 * Created by hzwukewei on 2017-6-6.
 */
class PopularListUserCase(private val hotRepository: HotRepository, threadExecutor: ThreadExecutor,
                          postExecutionThread: PostExecutionThread) : UseCase<PagePopularEntity>(threadExecutor, postExecutionThread) {

    private var page: Int = DomainConstanst.FIRST_PAGE
    private var word: String? = null

    fun setParam(page: Int, word: String?) {
        this.page = page
        this.word = word
    }

    override fun buildUseCaseObservable(): Observable<PagePopularEntity> {
        return hotRepository.getPopularList(page, word)
    }

}