package com.dynamicui.shared.runtime.binding

import com.dynamicui.shared.domain.value.BindingKey
import com.dynamicui.shared.domain.value.UiValue

interface BindingResolver {

    fun resolve(
        binding: BindingKey?,
        data: Map<BindingKey, UiValue>
    ): UiValue?
}