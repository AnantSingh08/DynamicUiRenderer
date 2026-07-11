package com.dynamicui.shared.domain.model

import com.dynamicui.shared.definition.ComponentDefinition
import com.dynamicui.shared.domain.value.LayoutId

data class LayoutDefinition(

    val id: LayoutId,

    val root: ComponentDefinition
)