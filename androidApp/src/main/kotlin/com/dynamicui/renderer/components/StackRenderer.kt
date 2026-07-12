package com.dynamicui.renderer.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.dynamicui.renderer.UiRenderer
import com.dynamicui.shared.model.UiAction
import com.dynamicui.shared.model.Orientation
import com.dynamicui.shared.model.StackNode

@Composable
fun StackRenderer(
    node: StackNode,
    modifier: Modifier,
    onAction: (UiAction) -> Unit
) {
    val content: @Composable () -> Unit = {

        node.children.forEach { child ->

            UiRenderer(
                node = child,
                onAction = onAction
            )
        }
    }

    when (node.orientation) {

        Orientation.VERTICAL ->
            Column(
                modifier = modifier
            ) {
                content()
            }

        Orientation.HORIZONTAL ->
            Row(
                modifier = modifier
            ) {
                content()
            }
    }
}