package com.dynamicui.shared.definition

import com.dynamicui.shared.model.UiAction
import com.dynamicui.shared.domain.value.ComponentId
import com.dynamicui.shared.domain.value.StyleId

data class CardDefinition(

    override val id: ComponentId,

    override val styleId: StyleId? = null,

    override val action: UiAction? = null,

    val children: List<ComponentDefinition>

) : ComponentDefinition