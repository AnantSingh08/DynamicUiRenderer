package com.dynamicui.shared.domain.model

import com.dynamicui.shared.domain.value.LayoutId
import com.dynamicui.shared.domain.value.StyleId
import com.dynamicui.shared.model.Style

data class UiDefinitions(
    val layouts: Map<LayoutId, LayoutDefinition>,

    val styles: Map<StyleId, Style>
)