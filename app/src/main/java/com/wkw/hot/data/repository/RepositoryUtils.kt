package com.wkw.hot.data.repository

import com.wkw.hot.data.exception.NetworkConnectionException
import com.wkw.hot.data.exception.ResponseException
import com.wkw.hot.domain.model.HotResponse
import io.reactivex.Observable
import io.reactivex.ObservableTransformer


/**
 * Created by hzwukewei on 2017-6-6.
 */
class RepositoryUtils {
    companion object {
        private val TAG = "RepositoryUtils"
        private val DEFAULT_ERROR_MSG = "Unknown error"
        fun <T> handleResult(): ObservableTransformer<HotResponse<T>, T> {
            return ObservableTransformer {
                it.flatMap {
                    if (it == null) {
                        Observable.error(NetworkConnectionException())
                    } else if (it.isSuccess()) {
                        createData(it.showapi_res_body)
                    } else {
                        Observable.error<T>(ResponseException(it))
                    }
                }
            }
        }


        private fun <T> createData(t: T): Observable<T> {
            return Observable.create {
                try {
                    it.onNext(t)
                    it.onComplete()
                } catch (e: Exception) {
                    it.onError(e)
                }
            }
        }
    }
}