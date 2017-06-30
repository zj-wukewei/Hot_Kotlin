package com.wkw.hot.domain.interactor

import io.reactivex.exceptions.OnErrorNotImplementedException
import io.reactivex.observers.DisposableObserver
import java.util.*

/**
 * Created by hzwukewei on 2017-6-6.
 */
class DefaultObserver<T> : DisposableObserver<T>() {
    private val onCompletedFunctions = ArrayList<() -> Unit>()
    private val onErrorFunctions = ArrayList<(e: Throwable) -> Unit>()
    private val onNextFunctions = ArrayList<(value: T) -> Unit>()
    private val onStartFunctions = ArrayList<() -> Unit>()

    override fun onComplete() = onCompletedFunctions.forEach { it() }

    override fun onError(e: Throwable?) = (e ?: RuntimeException("exception is unknown")).let { ex ->
        if (onErrorFunctions.isEmpty()) {
            throw OnErrorNotImplementedException(ex)
        } else {
            onErrorFunctions.forEach { it(ex) }
        }
    }

    override fun onNext(t: T) = onNextFunctions.forEach { it(t) }

    override fun onStart() = onStartFunctions.forEach { it() }

    fun onCompleted(onCompletedFunction: () -> Unit): DefaultObserver<T> = copy { onCompletedFunctions.add(onCompletedFunction) }
    fun onError(onErrorFunction: (t: Throwable) -> Unit): DefaultObserver<T> = copy { onErrorFunctions.add(onErrorFunction) }
    fun onNext(onNextFunction: (t: T) -> Unit): DefaultObserver<T> = copy { onNextFunctions.add(onNextFunction) }
    fun onStart(onStartFunction : () -> Unit) : DefaultObserver<T> = copy { onStartFunctions.add(onStartFunction) }

    private fun copy(block: DefaultObserver<T>.() -> Unit): DefaultObserver<T> {
        val newObserver = DefaultObserver<T>()
        newObserver.onCompletedFunctions.addAll(onCompletedFunctions)
        newObserver.onErrorFunctions.addAll(onErrorFunctions)
        newObserver.onNextFunctions.addAll(onNextFunctions)
        newObserver.onStartFunctions.addAll(onStartFunctions)

        newObserver.block()

        return newObserver
    }
}