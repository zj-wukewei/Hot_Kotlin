package com.wkw.hot.domain.interactor

import com.wkw.hot.domain.executor.PostExecutionThread
import com.wkw.hot.domain.executor.ThreadExecutor
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers


/**
 * Created by hzwukewei on 2017-6-6.
 */
abstract class UseCase<T>(val threadExecutor: ThreadExecutor, val postExecutionThread: PostExecutionThread) {
    private val disposables: CompositeDisposable = CompositeDisposable()

    abstract fun buildUseCaseObservable(): Observable<T>

    fun execute(observer: DisposableObserver<T>) {
        buildUseCaseObservable()
                .subscribeOn(Schedulers.from(threadExecutor))
                .observeOn(postExecutionThread.getScheduler())
                .subscribeWith(observer).addTo(disposables)
    }


    fun dispose() {
        if (!disposables.isDisposed) {
            disposables.dispose()
        }
    }
}