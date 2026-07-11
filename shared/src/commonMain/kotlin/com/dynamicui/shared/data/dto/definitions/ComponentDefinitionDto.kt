package com.dynamicui.shared.data.dto.definitions

import com.dynamicui.shared.data.dto.action.UiActionDto
import kotlinx.serialization.Serializable

@Serializable
sealed interface ComponentDefinitionDto {

    val id: String

    val styleId: String?

    val action: UiActionDto?
}