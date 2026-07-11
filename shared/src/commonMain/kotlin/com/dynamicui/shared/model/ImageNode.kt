package com.dynamicui.shared.model

import com.dynamicui.shared.domain.model.UiAction
import com.dynamicui.shared.domain.value.ComponentId
import com.dynamicui.shared.style.Style

data class ImageNode(

    override val id: ComponentId,

    override val style: Style?,

    override val action: UiAction?,

    val url: String

) : UiNode