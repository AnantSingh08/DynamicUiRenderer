package com.dynamicui.shared.data.dto.feed

import kotlinx.serialization.Serializable

@Serializable
data class FeedDto(
    val items: List<FeedItemDto>
)