package com.wkw.hot.view.presenter

import com.wkw.hot.domain.interactor.DefaultObserver
import com.wkw.hot.domain.interactor.PopularListUserCse
import com.wkw.hot.domain.model.PagePopularEntity
import com.wkw.hot.exception.createException
import com.wkw.hot.mapper.PopularMapper
import com.wkw.hot.util.showToast
import com.wkw.hot.view.contract.MainContract

/**
 * Created by hzwukewei on 2017-6-7.
 */
class MainPresenter(override var mView: MainContract.MainView,
                    private var popularMapper: PopularMapper,
                    private var getPopularListUserCse: PopularListUserCse) : MainContract.Presenter {

    override fun getPoplars(page: Int, word: String) {
        getView().showLoading()
        getPopularListUserCse.setParam(page, word)
        getPopularListUserCse.execute(object : DefaultObserver<PagePopularEntity>() {

            override fun onError(e: Throwable?) {
                super.onError(e)
                getView().hideLoading()
                getView().showRetry()
                val msg = getView().context().createException(e as Exception)
                getView().context().showToast(msg)
            }

            override fun onComplete() {
                super.onComplete()
                getView().hideLoading()
                getView().hideRetry()
            }

            override fun onNext(value: PagePopularEntity) {
                super.onNext(value)
                getView().showPoplars(popularMapper.transform(value.pagebean.contentlist))
            }
        })
    }

    override fun resume() {
    }

    override fun pause() {
    }

    override fun destroy() {
        getPopularListUserCse.dispose()
    }


}