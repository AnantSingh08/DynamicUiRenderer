package com.dynamicui.shared.model.node

import com.dynamicui.shared.domain.value.ComponentId
import com.dynamicui.shared.model.action.UiAction
import com.dynamicui.shared.model.style.Style

data class TextNode(

    override val id: ComponentId,

    override val style: Style?,

    override val action: UiAction?,

    val text: String

) : UiNode