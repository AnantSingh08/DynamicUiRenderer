package com.dynamicui.shared.domain.repository

import com.dynamicui.shared.domain.model.Feed

interface FeedRepository {

    suspend fun getFeed(
        screenId: String
    ): Feed
}