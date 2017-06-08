package com.wkw.hot

import android.app.Application
import com.wkw.hot.internal.di.ApplicationComponent
import com.wkw.hot.internal.di.DaggerApplicationComponent
import com.wkw.hot.internal.di.module.ApplicationModule
import com.wkw.hot.util.DelegatesExt

/**
 * Created by hzwukewei on 2017-6-6.
 */
class HotApp : Application() {

    companion object {
        var graph: ApplicationComponent by DelegatesExt.notNullSingleValue()
    }

    override fun onCreate() {
        super.onCreate()
        graph = DaggerApplicationComponent.builder()
                .applicationModule(ApplicationModule(this))
                .build()
    }
}