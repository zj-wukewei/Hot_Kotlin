package com.wkw.hot.domain.executor

import io.reactivex.Scheduler

/**
 * Created by hzwukewei on 2017-6-6.
 */
interface PostExecutionThread {
    fun getScheduler(): Scheduler
}