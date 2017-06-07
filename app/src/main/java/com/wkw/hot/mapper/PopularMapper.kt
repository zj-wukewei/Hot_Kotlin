package com.wkw.hot.mapper

import com.wkw.hot.domain.model.PopularEntity
import com.wkw.hot.model.PopularModel

/**
 * Created by hzwukewei on 2017-6-7.
 */
class PopularMapper {
    fun transform(populars: List<PopularEntity>): List<PopularModel> {
        return populars.filter { it.title == null }.mapNotNull { transform(it) }
    }

    fun transform(popularEntity: PopularEntity) = popularEntity.title?.let {
        PopularModel(popularEntity.ctime, popularEntity.title, popularEntity.description, popularEntity.picUrl, popularEntity.url)
    }

}