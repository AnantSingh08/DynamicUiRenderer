package com.dynamicui.shared.data.dto.definitions

import kotlinx.serialization.Serializable

@Serializable
data class LayoutDefinitionDto(

    val id: String,

    val root: ComponentDefinitionDto
)