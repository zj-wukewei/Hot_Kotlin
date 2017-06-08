package com.wkw.hot.internal.di.subcomponents.main

import com.wkw.hot.domain.executor.PostExecutionThread
import com.wkw.hot.domain.executor.ThreadExecutor
import com.wkw.hot.domain.interactor.PopularListUserCse
import com.wkw.hot.domain.repository.HotRepository
import com.wkw.hot.internal.PerActivity
import com.wkw.hot.mapper.PopularMapper
import com.wkw.hot.view.contract.MainContract
import com.wkw.hot.view.fragment.MainFragment
import com.wkw.hot.view.presenter.MainPresenter
import dagger.Module
import dagger.Provides

/**
 * Created by hzwukewei on 2017-6-6.
 */
@Module
class MainModule(val fragment: MainFragment) {

    @Provides
    @PerActivity
    fun provideMainView(): MainContract.MainView = fragment as MainContract.MainView

    @Provides
    @PerActivity
    fun providePopularMapper() = PopularMapper()

    @Provides
    @PerActivity
    fun providePopularList(hotRepository: HotRepository, threadExecutor: ThreadExecutor,
                           postExecutionThread: PostExecutionThread): PopularListUserCse = PopularListUserCse(hotRepository, threadExecutor, postExecutionThread)

    @Provides
    @PerActivity
    fun provideManPresenter(view: MainContract.MainView,
                            popularMapper: PopularMapper,
                            popularListUserCse: PopularListUserCse) = MainPresenter(view, popularMapper, popularListUserCse)
}