package com.dynamicui.shared.model

import com.dynamicui.shared.domain.model.UiAction
import com.dynamicui.shared.domain.value.ComponentId
import com.dynamicui.shared.style.Style

data class TextNode(

    override val id: ComponentId,

    override val style: Style?,

    override val action: UiAction?,

    val text: String

) : UiNode