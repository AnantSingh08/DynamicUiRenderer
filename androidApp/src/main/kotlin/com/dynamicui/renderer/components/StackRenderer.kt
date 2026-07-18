package com.dynamicui.renderer.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.dynamicui.presentation.renderer.modifier.applyStyle
import com.dynamicui.renderer.UiRenderer
import com.dynamicui.renderer.extensions.padding
import com.dynamicui.renderer.mappers.AlignmentMapper
import com.dynamicui.shared.model.common.Orientation
import com.dynamicui.shared.model.node.StackNode
import com.dynamicui.shared.model.action.UiAction

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

    val stackModifier = modifier
        .applyStyle(node.style)
        .padding(node.style?.padding)

    when (node.orientation) {

        Orientation.VERTICAL ->
            Column(
                modifier = stackModifier,
                horizontalAlignment = AlignmentMapper.horizontal(
                    node.style?.alignment
                )
            ) {
                content()
            }

        Orientation.HORIZONTAL ->
            Row(
                modifier = stackModifier,
                verticalAlignment = AlignmentMapper.vertical(
                    node.style?.alignment
                )
            ) {
                content()
            }
    }
}