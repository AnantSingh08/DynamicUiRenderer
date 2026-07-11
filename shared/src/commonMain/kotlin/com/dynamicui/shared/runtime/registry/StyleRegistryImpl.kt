package com.dynamicui.shared.runtime.registry

import com.dynamicui.shared.domain.value.StyleId
import com.dynamicui.shared.style.Style

class StyleRegistryImpl : StyleRegistry {

    private val styles =
        mutableMapOf<StyleId, Style>()

    override fun registerStyles(
        styles: Map<StyleId, Style>
    ) {
        this.styles.clear()
        this.styles.putAll(styles)
    }

    override fun getStyle(
        styleId: StyleId
    ): Style? {
        return styles[styleId]
    }

    override fun clear() {
        styles.clear()
    }
}