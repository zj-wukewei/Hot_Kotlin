package com.wkw.hot.domain.interactor

import com.wkw.hot.domain.executor.PostExecutionThread
import com.wkw.hot.domain.executor.ThreadExecutor
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers


/**
 * Created by hzwukewei on 2017-6-6.
 */
abstract class UseCase<T>(val threadExecutor: ThreadExecutor, val postExecutionThread: PostExecutionThread) {
    private val disposables: CompositeDisposable = CompositeDisposable()

    abstract fun buildUseCaseObservable(): Observable<T>

    fun execute(observer: DisposableObserver<T>) {
        val observable: Observable<T> = buildUseCaseObservable()
                .subscribeOn(Schedulers.from(threadExecutor))
                .observeOn(postExecutionThread.getScheduler())
        addDisposable(observable.subscribeWith(observer))
    }


    fun dispose() {
        if (!disposables.isDisposed) {
            disposables.dispose()
        }
    }

    private fun addDisposable(disposable: Disposable) {
        disposables.add(disposable)
    }
}