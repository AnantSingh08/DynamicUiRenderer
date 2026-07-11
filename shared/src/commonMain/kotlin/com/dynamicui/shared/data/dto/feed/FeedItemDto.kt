package com.dynamicui.shared.data.dto.feed

import com.dynamicui.shared.data.dto.action.UiActionDto
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

@Serializable
data class FeedItemDto(

    val id: String,

    val layoutId: String,

    val data: Map<String, JsonElement>,

    val action: UiActionDto? = null
)