package com.dynamicui.presentation.common

sealed interface UiEvent {

    data class Navigate(
        val destination: String
    ) : UiEvent

    data class ShowToast(
        val message: String
    ) : UiEvent
}