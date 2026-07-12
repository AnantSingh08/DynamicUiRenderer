package com.dynamicui.shared.model

import com.dynamicui.shared.domain.value.ComponentId

data class TextNode(

    override val id: ComponentId,

    override val style: Style?,

    override val action: UiAction?,

    val text: String

) : UiNode