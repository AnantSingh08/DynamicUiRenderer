package com.dynamicui.presentation.details

import com.dynamicui.shared.model.UiNode

sealed interface DetailsUiState {

    data object Loading : DetailsUiState

    data class Success(
        val nodes: List<UiNode>
    ) : DetailsUiState

    data class Error(
        val message: String
    ) : DetailsUiState
}