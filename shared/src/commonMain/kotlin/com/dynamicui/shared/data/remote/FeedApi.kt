package com.dynamicui.shared.data.remote

import com.dynamicui.shared.data.dto.feed.FeedDto
import com.dynamicui.shared.data.network.ApiConfig
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class FeedApi(
    private val client: HttpClient
) {

    suspend fun getFeed(
        screenId: String
    ): FeedDto {

        return client
            .get("${ApiConfig.FEED}/$screenId")
            .body()
    }
}