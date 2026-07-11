package com.dynamicui.shared.runtime.registry

import com.dynamicui.shared.domain.model.LayoutDefinition
import com.dynamicui.shared.domain.value.LayoutId

interface LayoutRegistry {

    fun registerLayouts(
        layouts: Map<LayoutId, LayoutDefinition>
    )

    fun getLayout(
        layoutId: LayoutId
    ): LayoutDefinition?

    fun clear()
}