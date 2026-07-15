package com.dynamicui.presentation.home

import com.dynamicui.shared.model.UiNode

sealed interface HomeUIState {

    data object Loading: HomeUIState

    data class Success(
        val nodes: List<UiNode>
    ): HomeUIState

    data class Error(
        val message: String
    ): HomeUIState
}