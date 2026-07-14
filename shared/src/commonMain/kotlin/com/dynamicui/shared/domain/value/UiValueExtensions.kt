package com.dynamicui.shared.domain.value

fun UiValue.asString(): String? =
    (this as? StringValue)?.value

fun UiValue.asNumber(): Double? =
    (this as? NumberValue)?.value

fun UiValue.asBoolean(): Boolean? =
    (this as? BooleanValue)?.value

fun UiValue.asObject(): Map<BindingKey, UiValue>? =
    (this as? ObjectValue)?.values

fun UiValue.asList(): List<UiValue>? =
    (this as? ListValue)?.values

fun UiValue.isNull(): Boolean =
    this is NullValue