package com.dynamicui.presentation.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.dynamicui.renderer.UiRenderer

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel()
) {

    when (
        val state = viewModel.uiState.collectAsState().value
    ) {
        HomeUIState.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        is HomeUIState.Success -> {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .safeDrawingPadding()
            ) {

                UiRenderer(
                    nodes = state.nodes
                )
            }
        }

        is HomeUIState.Error -> {
            Text(
                text = state.message,
                color = colorScheme.error
            )
        }
    }
}