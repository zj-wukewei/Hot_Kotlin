package com.wkw.hot.internal.di

import com.wkw.hot.internal.di.module.ApiModule
import com.wkw.hot.internal.di.module.ApplicationModule
import com.wkw.hot.internal.di.subcomponents.main.MainModule
import com.wkw.hot.internal.di.module.RepositoryModule
import com.wkw.hot.internal.di.subcomponents.main.MainComponent
import dagger.Component
import javax.inject.Singleton

/**
 * Created by hzwukewei on 2017-6-6.
 */
@Singleton
@Component(modules = arrayOf(
        ApplicationModule::class,
        ApiModule::class,
        RepositoryModule::class
))
interface ApplicationComponent {
    fun plus(hotModule: MainModule): MainComponent
}