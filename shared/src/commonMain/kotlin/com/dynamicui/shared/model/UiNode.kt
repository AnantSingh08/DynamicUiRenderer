package com.dynamicui.shared.model

import com.dynamicui.shared.domain.model.UiAction
import com.dynamicui.shared.domain.value.ComponentId
import com.dynamicui.shared.style.Style

sealed interface UiNode {

    val id: ComponentId

    val style: Style?

    val action: UiAction?
}