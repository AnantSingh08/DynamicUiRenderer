package com.dynamicui.presentation.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dynamicui.shared.bootstrap.DynamicUiRenderer
import com.dynamicui.shared.model.NavigateAction
import com.dynamicui.shared.model.ToastAction
import com.dynamicui.shared.model.UiAction
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
class DetailsViewModel @Inject constructor(
    private val renderer: DynamicUiRenderer
) : ViewModel() {

    private val _uiState =
        MutableStateFlow<DetailsUiState>(
            DetailsUiState.Loading
        )

    val uiState: StateFlow<DetailsUiState> =
        _uiState.asStateFlow()

    private val _events = MutableSharedFlow<DetailsUiEvent>()

    val events: SharedFlow<DetailsUiEvent> =
        _events.asSharedFlow()

    init {
        loadHome()
    }

    private fun loadHome() {

        viewModelScope.launch {

            runCatching {

                renderer.resolveScreen(
                    screenId = "details"
                )

            }.onSuccess { nodes ->

                _uiState.value =
                    DetailsUiState.Success(nodes)

            }.onFailure {

                _uiState.value =
                    DetailsUiState.Error(
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
                        DetailsUiEvent.Navigate(
                            destination = action.destination
                        )
                    )
                }

                is ToastAction -> {
                    _events.emit(
                        DetailsUiEvent.ShowToast(
                            message = action.message
                        )
                    )
                }
            }
        }
    }
}