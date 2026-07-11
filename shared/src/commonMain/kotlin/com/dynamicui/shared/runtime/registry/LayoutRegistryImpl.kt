package com.dynamicui.shared.runtime.registry

import com.dynamicui.shared.domain.model.LayoutDefinition
import com.dynamicui.shared.domain.value.LayoutId

class LayoutRegistryImpl : LayoutRegistry {

    private val layouts =
        mutableMapOf<LayoutId, LayoutDefinition>()

    override fun registerLayouts(
        layouts: Map<LayoutId, LayoutDefinition>
    ) {
        this.layouts.clear()
        this.layouts.putAll(layouts)
    }

    override fun getLayout(
        layoutId: LayoutId
    ): LayoutDefinition? {
        return layouts[layoutId]
    }

    override fun clear() {
        layouts.clear()
    }
}