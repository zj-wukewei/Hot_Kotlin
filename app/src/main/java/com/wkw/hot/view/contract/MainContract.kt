package com.wkw.hot.view.contract

import com.wkw.hot.model.PopularModel
import com.wkw.hot.view.base.LoadDataView
import com.wkw.hot.view.base.MvpPresenter

/**
 * Created by hzwukewei on 2017-6-7.
 */
interface MainContract {
    interface MainView : LoadDataView {
        fun showPoplars(populars: List<PopularModel>?)
    }

    interface Presenter : MvpPresenter<MainView> {
        fun getPoplars(page: Int, word: String)
    }
}