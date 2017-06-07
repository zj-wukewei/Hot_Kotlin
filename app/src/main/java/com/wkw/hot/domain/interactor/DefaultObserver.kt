package com.wkw.hot.domain.interactor

import io.reactivex.observers.DisposableObserver

/**
 * Created by hzwukewei on 2017-6-6.
 */
open class DefaultObserver<T> : DisposableObserver<T>() {
    override fun onNext(value: T) {
    }

    override fun onError(e: Throwable?) {
    }

    override fun onComplete() {
    }
}