package com.dynamicui.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dynamicui.presentation.common.ScreenUiState
import com.dynamicui.presentation.common.UiEvent
import com.dynamicui.shared.bootstrap.DynamicUiRenderer
import com.dynamicui.shared.model.action.NavigateAction
import com.dynamicui.shared.model.action.ToastAction
import com.dynamicui.shared.model.action.UiAction
import com.dynamicui.shared.model.node.UiNode
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val renderer: DynamicUiRenderer
) : ViewModel() {

    private val _uiState =
        MutableStateFlow<ScreenUiState<List<UiNode>>>(
            ScreenUiState.Loading
        )

    val uiState: StateFlow<ScreenUiState<List<UiNode>>> =
        _uiState.asStateFlow()

    private val _events = MutableSharedFlow<UiEvent>()

    val events: SharedFlow<UiEvent> =
        _events.asSharedFlow()

    init {
        loadHome()
    }

    private fun loadHome() {

        viewModelScope.launch {

            runCatching {

                renderer.resolveScreen(
                    screenId = "home"
                )

            }.onSuccess { nodes ->

                _uiState.value =
                    ScreenUiState.Success(nodes)

            }.onFailure {

                _uiState.value =
                    ScreenUiState.Error(
                        it.message ?: "Unknown error"
                    )
            }
        }
    }

    fun onAction(action: UiAction) {

        viewModelScope.launch {

            when (action) {

                is NavigateAction -> {
                    _events.emit(
                        UiEvent.Navigate(
                            destination = action.destination
                        )
                    )
                }

                is ToastAction -> {
                    _events.emit(
                        UiEvent.ShowToast(
                            message = action.message
                        )
                    )
                }
            }
        }
    }
}