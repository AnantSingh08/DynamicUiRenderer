package com.dynamicui.shared.runtime.registry

import com.dynamicui.shared.domain.value.StyleId
import com.dynamicui.shared.model.style.Style

interface StyleRegistry {

    fun registerStyles(
        styles: Map<StyleId, Style>
    )

    fun getStyle(
        styleId: StyleId
    ): Style?

    fun clear()
}