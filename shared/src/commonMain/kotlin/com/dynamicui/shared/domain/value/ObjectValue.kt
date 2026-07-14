package com.dynamicui.shared.domain.value


data class ObjectValue(
    val values: Map<BindingKey, UiValue>
): UiValue

