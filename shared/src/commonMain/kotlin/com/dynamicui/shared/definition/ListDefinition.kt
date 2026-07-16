package com.dynamicui.shared.definition

import com.dynamicui.shared.domain.value.BindingKey
import com.dynamicui.shared.domain.value.ComponentId
import com.dynamicui.shared.domain.value.StyleId
import com.dynamicui.shared.model.common.Orientation
import com.dynamicui.shared.model.action.UiAction

data class ListDefinition(

    override val id: ComponentId,

    val orientation: Orientation,

    val binding: BindingKey,

    override val styleId: StyleId? = null,

    override val action: UiAction? = null,

    val children: List<ComponentDefinition>

) : ComponentDefinition