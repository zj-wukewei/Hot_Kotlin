package com.wkw.hot.view.presenter

import com.wkw.hot.domain.interactor.DefaultObserver
import com.wkw.hot.domain.interactor.PopularListUserCase
import com.wkw.hot.domain.model.PagePopularEntity
import com.wkw.hot.mapper.PopularMapper
import com.wkw.hot.view.contract.MainContract

/**
 * Created by hzwukewei on 2017-6-7.
 */
class MainPresenter(override var mView: MainContract.MainView,
                    private var popularMapper: PopularMapper,
                    private var getPopularListUserCase: PopularListUserCase) : MainContract.Presenter {

    override fun getPoplars(page: Int, word: String) {
        getView().loading()
        getPopularListUserCase.setParam(page, word)
//        getPopularListUserCase.execute(object : DefaultObserver<PagePopularEntity>() {
//
//            override fun onError(e: Throwable?) {
//                super.onError(e)
//                getView().showError(Exception(e))
//            }
//
//            override fun onComplete() {
//                super.onComplete()
//                getView().loadFinish()
//            }
//
//            override fun onNext(value: PagePopularEntity) {
//                super.onNext(value)
//                getView().showPoplars(popularMapper.transform(value.pagebean.contentlist))
//            }
//        })
        getPopularListUserCase.execute(DefaultObserver<PagePopularEntity>()
                .onError {
                    getView().showError(Exception(it))
                }
                .onCompleted {
                    getView().loadFinish()
                }
                .onNext {
                    getView().showPoplars(popularMapper.transform(it.pagebean.contentlist))
                })
    }

    override fun resume() {
    }

    override fun pause() {
    }

    override fun destroy() {
        getPopularListUserCase.dispose()
    }


}