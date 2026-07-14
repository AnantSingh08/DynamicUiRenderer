package com.dynamicui.shared.definition

import com.dynamicui.shared.domain.value.BindingKey
import com.dynamicui.shared.domain.value.ComponentId
import com.dynamicui.shared.domain.value.StyleId
import com.dynamicui.shared.model.UiAction

data class ImageDefinition(

    override val id: ComponentId,

    override val styleId: StyleId? = null,

    override val action: UiAction? = null,

    val url: String? = null,

    val binding: BindingKey? = null

) : ComponentDefinition