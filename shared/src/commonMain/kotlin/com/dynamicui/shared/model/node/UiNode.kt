package com.dynamicui.shared.model.node

import com.dynamicui.shared.domain.value.ComponentId
import com.dynamicui.shared.model.action.UiAction
import com.dynamicui.shared.model.style.Style

sealed interface UiNode {

    val id: ComponentId

    val style: Style?

    val action: UiAction?
}