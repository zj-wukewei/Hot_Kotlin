package com.wkw.hot.internal.di.module

import com.wkw.hot.HotApp
import com.wkw.hot.UIThread
import com.wkw.hot.data.executor.JobExecutor
import com.wkw.hot.domain.executor.PostExecutionThread
import com.wkw.hot.domain.executor.ThreadExecutor
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by hzwukewei on 2017-6-6.
 */
@Module
class ApplicationModule(private val app: HotApp) {

    @Provides
    @Singleton
    fun provideApplication(): HotApp = app

    @Provides
    @Singleton
    fun provideThreadExecutor(): ThreadExecutor = JobExecutor()

    @Provides
    @Singleton
    fun providePostExecutionThread(): PostExecutionThread = UIThread()
}