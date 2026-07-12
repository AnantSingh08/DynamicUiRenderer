package com.dynamicui.shared.model

import com.dynamicui.shared.domain.value.ComponentId

sealed interface UiNode {

    val id: ComponentId

    val style: Style?

    val action: UiAction?
}