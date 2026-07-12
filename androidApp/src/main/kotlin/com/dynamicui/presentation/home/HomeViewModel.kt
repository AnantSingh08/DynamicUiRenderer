package com.dynamicui.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dynamicui.shared.bootstrap.DynamicUiRenderer
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val renderer: DynamicUiRenderer
) : ViewModel() {

    private val _uiState = MutableStateFlow<HomeUIState>(
        HomeUIState.Loading
    )

    val uiState: StateFlow<HomeUIState> = _uiState.asStateFlow()

    init {
        onEvent(HomeEvent.LoadScreen)
    }

    fun onEvent(
        event: HomeEvent
    ) {
        when (event) {
            HomeEvent.LoadScreen -> {
                loadHome()
            }
        }
    }

    private fun loadHome() {
        viewModelScope.launch {

            runCatching {

                renderer.resolveScreen(
                    screenId = "home"
                )

            }.onSuccess { nodes ->
                _uiState.value = HomeUIState.Success(nodes)
            }.onFailure {
                _uiState.value = HomeUIState.Error(
                    message = it.message ?: "Something went wrong (Unknown Error)"
                )
            }
        }
    }
}