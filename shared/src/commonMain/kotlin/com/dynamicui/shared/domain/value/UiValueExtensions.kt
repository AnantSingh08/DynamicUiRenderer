package com.dynamicui.shared.domain.value

fun UiValue.asString(): String? =
    (this as? StringValue)?.value

fun UiValue.asNumber(): Double? =
    (this as? NumberValue)?.value

fun UiValue.asBoolean(): Boolean? =
    (this as? BooleanValue)?.value

fun UiValue.asObject(): Map<BindingKey, UiValue>? =
    (this as? ObjectValue)?.value

fun UiValue.asList(): List<UiValue>? =
    (this as? ListValue)?.value

fun UiValue.isNull(): Boolean =
    this is NullValue