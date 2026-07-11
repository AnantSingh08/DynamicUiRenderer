package com.dynamicui.shared.data.dto.definitions

import kotlinx.serialization.Serializable

@Serializable
data class StyleDefinitionDto(

    val id: String,

    val backgroundColor: String?,

    val textColor: String?,

    val padding: Int?,

    val cornerRadius: Int?
)
