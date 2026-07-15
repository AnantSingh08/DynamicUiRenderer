package com.dynamicui.presentation.home

sealed interface HomeUiEvent {

    data class Navigate(
        val destination: String
    ) : HomeUiEvent

    data class ShowToast(
        val message: String
    ) : HomeUiEvent
}