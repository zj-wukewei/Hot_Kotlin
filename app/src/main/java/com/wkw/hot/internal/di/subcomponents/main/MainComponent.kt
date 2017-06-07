package com.wkw.hot.internal.di.subcomponents.main

import com.wkw.hot.internal.PerActivity
import com.wkw.hot.view.activity.MainActivity
import dagger.Subcomponent

/**
 * Created by hzwukewei on 2017-6-6.
 */
@PerActivity
@Subcomponent(modules = arrayOf(
        MainModule::class
))
interface MainComponent {
    fun injectTo(activity: MainActivity)
}