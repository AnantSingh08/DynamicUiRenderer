package com.dynamicui.shared.data.dto.action

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
sealed interface UiActionDto {
}

@Serializable
@SerialName("navigate")
data class NavigateActionDto(
    val destination: String,
    val params: Map<String, String> = emptyMap(),
) : UiActionDto

@Serializable
@SerialName("toast")
data class ToastActionDto(
    val message: String,
) : UiActionDto
