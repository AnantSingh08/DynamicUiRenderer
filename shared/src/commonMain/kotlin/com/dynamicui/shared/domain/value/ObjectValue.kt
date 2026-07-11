package com.dynamicui.shared.domain.value


data class ObjectValue(
    val value: Map<BindingKey, UiValue>
): UiValue

