package com.dynamicui.shared.data.dto.definitions

import com.dynamicui.shared.data.dto.action.UiActionDto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("stack")
data class StackDefinitionDto(

    override val id: String,

    val orientation: String,

    override val styleId: String? = null,

    override val action: UiActionDto? = null,

    val children: List<ComponentDefinitionDto>

) : ComponentDefinitionDto