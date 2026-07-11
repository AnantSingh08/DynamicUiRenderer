package com.dynamicui.shared.bootstrap

import com.dynamicui.shared.domain.usecase.InitializeDefinitionsUseCase
import com.dynamicui.shared.domain.usecase.ResolveScreenUseCase
import com.dynamicui.shared.model.UiNode
import com.dynamicui.shared.runtime.state.InitializationState
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

class DynamicUiRenderer(
    private val initializeDefinitionsUseCase: InitializeDefinitionsUseCase,
    private val resolveScreenUseCase: ResolveScreenUseCase
) {

    private val initializationMutex = Mutex()

    private var initializationState: InitializationState =
        InitializationState.NotInitialized

    suspend fun resolveScreen(
        screenId: String
    ): List<UiNode> {

        ensureInitialized()

        return resolveScreenUseCase(screenId)
    }

    private suspend fun ensureInitialized() {

        if (initializationState == InitializationState.Initialized) {
            return
        }

        initializationMutex.withLock {

            if (initializationState == InitializationState.Initialized) {
                return
            }

            initializeDefinitionsUseCase()

            initializationState =
                InitializationState.Initialized
        }
    }
}