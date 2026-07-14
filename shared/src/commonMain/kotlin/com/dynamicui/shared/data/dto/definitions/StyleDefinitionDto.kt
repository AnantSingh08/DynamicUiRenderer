package com.dynamicui.shared.data.dto.definitions

import kotlinx.serialization.Serializable

@Serializable
data class StyleDefinitionDto(

    val id: String,

    val width: Int? = null,

    val height: Int? = null,

    val padding: String? = null,

    val margin: String? = null,

    val spacing: Int? = null,

    val backgroundColor: String? = null,

    val textColor: String? = null,

    val fontSize: Int? = null,

    val fontWeight: String? = null,

    val cornerRadius: String? = null,

    val alignment: String? = null
)
