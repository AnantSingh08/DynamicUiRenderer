package com.dynamicui.shared.model

sealed interface UiAction

data class NavigateAction(
    val destination: String,
    val params: Map<String, String> = emptyMap(),
) : UiAction

data class ToastAction(
    val message: String,
) : UiAction
