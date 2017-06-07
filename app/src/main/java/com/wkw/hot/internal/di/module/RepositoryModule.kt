package com.wkw.hot.internal.di.module

import com.wkw.hot.data.repository.HotDataRepository
import com.wkw.hot.domain.repository.HotRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by hzwukewei on 2017-6-6.
 */
@Module
class RepositoryModule {
    @Provides
    @Singleton
    fun hotRepository(hotDataRepository: HotDataRepository): HotRepository = hotDataRepository
}