package com.dynamicui.shared.runtime.binding

import com.dynamicui.shared.domain.value.BindingKey
import com.dynamicui.shared.domain.value.UiValue

data class BindingContext(
    val data: Map<BindingKey, UiValue>
)