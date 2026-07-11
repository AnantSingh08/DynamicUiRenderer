package com.dynamicui.shared.data.dto.definitions

import kotlinx.serialization.Serializable

@Serializable
data class UiDefinitionsDto(

    val layouts: List<LayoutDefinitionDto>,

    val styles: List<StyleDefinitionDto>
)