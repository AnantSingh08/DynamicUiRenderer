package com.dynamicui.shared.model

import com.dynamicui.shared.domain.value.ComponentId

data class ImageNode(

    override val id: ComponentId,

    override val style: Style?,

    override val action: UiAction?,

    val url: String

) : UiNode