package com.dynamicui.renderer

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.dynamicui.presentation.renderer.components.TextRenderer
import com.dynamicui.renderer.components.CardRenderer
import com.dynamicui.renderer.components.ImageRenderer
import com.dynamicui.renderer.components.ListRenderer
import com.dynamicui.renderer.components.StackRenderer
import com.dynamicui.shared.model.node.CardNode
import com.dynamicui.shared.model.node.ImageNode
import com.dynamicui.shared.model.node.ListNode
import com.dynamicui.shared.model.node.StackNode
import com.dynamicui.shared.model.node.TextNode
import com.dynamicui.shared.model.action.UiAction
import com.dynamicui.shared.model.node.UiNode

@Composable
fun UiRenderer(
    node: UiNode,
    modifier: Modifier = Modifier,
    onAction: (UiAction) -> Unit = {}
) {
    when (node) {

        is StackNode ->
            StackRenderer(
                node = node,
                modifier = modifier,
                onAction = onAction
            )

        is TextNode ->
            TextRenderer(
                node = node,
                modifier = modifier,
                onAction = onAction
            )

        is CardNode ->
            CardRenderer(
                node = node,
                modifier = modifier,
                onAction = onAction
            )

        is ImageNode ->
            ImageRenderer(
                node = node,
                modifier = modifier,
                onAction = onAction
            )

        is ListNode ->
            ListRenderer(
                node = node,
                modifier = modifier,
                onAction = onAction
            )
    }
}

@Composable
fun UiRenderer(
    nodes: List<UiNode>,
    modifier: Modifier = Modifier,
    onAction: (UiAction) -> Unit = {}
) {
    nodes.forEach { node ->
        UiRenderer(
            node = node,
            modifier = modifier,
            onAction = onAction
        )
    }
}