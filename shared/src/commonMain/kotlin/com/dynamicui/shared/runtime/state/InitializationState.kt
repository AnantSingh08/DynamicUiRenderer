package com.dynamicui.shared.runtime.state

internal sealed interface InitializationState {

    data object NotInitialized : InitializationState

    data object Initialized : InitializationState
}