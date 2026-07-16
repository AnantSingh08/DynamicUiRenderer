package com.dynamicui.renderer.action

import androidx.compose.foundation.clickable
import androidx.compose.ui.Modifier
import com.dynamicui.shared.model.action.UiAction

fun Modifier.clickableAction(
    action: UiAction?,
    onAction: (UiAction) -> Unit
): Modifier {

    if (action == null) {
        return this
    }

    return clickable {
        onAction(action)
    }
}