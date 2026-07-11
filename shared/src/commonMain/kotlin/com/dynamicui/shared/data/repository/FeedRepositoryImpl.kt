package com.dynamicui.shared.data.repository

import com.dynamicui.shared.data.mapper.FeedMapper
import com.dynamicui.shared.data.remote.FeedApi
import com.dynamicui.shared.domain.model.Feed
import com.dynamicui.shared.domain.repository.FeedRepository

class FeedRepositoryImpl(
    private val feedApi: FeedApi,
    private val feedMapper: FeedMapper
) : FeedRepository {

    override suspend fun getFeed(
        screenId: String
    ): Feed {

        val dto = feedApi.getFeed(screenId)

        return feedMapper.map(dto)
    }
}