package com.dynamicui.renderer.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.dynamicui.presentation.renderer.modifier.ModifierMapper
import com.dynamicui.renderer.UiRenderer
import com.dynamicui.renderer.extensions.padding
import com.dynamicui.shared.model.Orientation
import com.dynamicui.shared.model.StackNode
import com.dynamicui.shared.model.UiAction

@Composable
fun StackRenderer(
    node: StackNode,
    modifier: Modifier,
    onAction: (UiAction) -> Unit
) {
    val content: @Composable () -> Unit = {

        UiRenderer(
            nodes = node.children,
            onAction = onAction
        )
    }

    when (node.orientation) {

        Orientation.VERTICAL ->
            Column(
                modifier = ModifierMapper
                    .map(
                        style = node.style,
                        modifier = modifier
                    )
                    .padding(node.style?.padding)
            ) {
                content()
            }

        Orientation.HORIZONTAL ->
            Row(
                modifier = ModifierMapper
                    .map(
                        style = node.style,
                        modifier = modifier
                    )
                    .padding(node.style?.padding)
            ) {
                content()
            }
    }
}