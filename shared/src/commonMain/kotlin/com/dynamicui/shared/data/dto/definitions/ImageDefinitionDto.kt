package com.dynamicui.shared.data.dto.definitions

import com.dynamicui.shared.data.dto.action.UiActionDto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("image")
data class ImageDefinitionDto(

    override val id: String,

    override val styleId: String? = null,

    override val action: UiActionDto? = null,

    val url: String? = null,

    val binding: String? = null

) : ComponentDefinitionDto