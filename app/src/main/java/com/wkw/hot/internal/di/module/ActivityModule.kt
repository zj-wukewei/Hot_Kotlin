package com.wkw.hot.internal.di.module

import android.content.Context
import android.support.v7.app.AppCompatActivity
import com.wkw.hot.internal.PerActivity
import dagger.Module
import dagger.Provides

/**
 * Created by hzwukewei on 2017-6-6.
 */
@Module
abstract class ActivityModule(protected val activity: AppCompatActivity) {
    @Provides
    @PerActivity
    fun provideActivity(): AppCompatActivity = activity

    @Provides
    @PerActivity
    fun provideActiviyContext(): Context = activity.baseContext
}