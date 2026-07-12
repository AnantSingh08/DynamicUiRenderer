package com.dynamicui.shared.definition

import com.dynamicui.shared.model.UiAction
import com.dynamicui.shared.domain.value.ComponentId
import com.dynamicui.shared.domain.value.StyleId
import com.dynamicui.shared.domain.value.BindingKey

data class TextDefinition(

    override val id: ComponentId,

    override val styleId: StyleId? = null,

    override val action: UiAction? = null,

    val text: String? = null,

    val binding: BindingKey? = null

) : ComponentDefinition