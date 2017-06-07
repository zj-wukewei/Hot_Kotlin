package com.wkw.hot

import com.wkw.hot.domain.executor.PostExecutionThread
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import javax.inject.Singleton

/**
 * Created by hzwukewei on 2017-6-6.
 */
@Singleton
class UIThread : PostExecutionThread {
    override fun getScheduler(): Scheduler {
        return AndroidSchedulers.mainThread()
    }
}