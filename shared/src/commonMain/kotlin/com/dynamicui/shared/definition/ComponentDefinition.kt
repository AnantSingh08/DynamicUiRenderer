package com.dynamicui.shared.definition

import com.dynamicui.shared.model.UiAction
import com.dynamicui.shared.domain.value.ComponentId
import com.dynamicui.shared.domain.value.StyleId

sealed interface ComponentDefinition {

    val id: ComponentId

    val styleId: StyleId?

    val action: UiAction?
}