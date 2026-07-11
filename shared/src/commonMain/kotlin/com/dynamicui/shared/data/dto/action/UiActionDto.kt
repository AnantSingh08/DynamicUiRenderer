package com.dynamicui.shared.data.dto.action

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
sealed interface UiActionDto {
    val type: String
}

@Serializable
@SerialName("navigate")
data class NavigateActionDto(
    override val type: String = "navigate",
    val destination: String,
    val params: Map<String, String> = emptyMap(),
) : UiActionDto

@Serializable
@SerialName("toast")
data class ToastActionDto(
    override val type: String = "toast",
    val message: String,
) : UiActionDto
