package com.dynamicui.presentation.details

sealed interface DetailsUiEvent {

    data class Navigate(
        val destination: String
    ) : DetailsUiEvent

    data class ShowToast(
        val message: String
    ) : DetailsUiEvent
}