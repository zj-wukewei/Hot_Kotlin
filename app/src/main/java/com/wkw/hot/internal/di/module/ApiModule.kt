package com.wkw.hot.internal.di.module

import com.wkw.hot.data.api.HotApi
import com.wkw.hot.data.api.HotService
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by hzwukewei on 2017-6-6.
 */
@Module
class ApiModule {

    @Provides
    @Singleton
    fun getHotApi(): HotApi = createApi(HotApi::class.java)

    fun <T> createApi(clazz: Class<T>): T {
        return HotService.createApi(clazz)
    }
}