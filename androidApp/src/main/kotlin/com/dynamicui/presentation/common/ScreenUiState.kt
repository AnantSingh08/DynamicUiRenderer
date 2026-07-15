package com.dynamicui.presentation.common

sealed interface ScreenUiState<out T> {
    data object Loading : ScreenUiState<Nothing>

    data class Success<T>(
        val data: T
    ) : ScreenUiState<T>

    data class Error(
        val message: String
    ) : ScreenUiState<Nothing>

}