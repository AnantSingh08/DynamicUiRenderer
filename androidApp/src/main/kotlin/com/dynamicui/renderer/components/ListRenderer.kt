package com.dynamicui.renderer.components

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.dynamicui.presentation.renderer.modifier.applyStyle
import com.dynamicui.renderer.UiRenderer
import com.dynamicui.renderer.extensions.padding
import com.dynamicui.shared.model.node.ListNode
import com.dynamicui.shared.model.common.Orientation
import com.dynamicui.shared.model.action.UiAction

@Composable
fun ListRenderer(
    node: ListNode,
    modifier: Modifier = Modifier,
    onAction: (UiAction) -> Unit
) {

    val mappedModifier = modifier
        .applyStyle(node.style)
        .padding(node.style?.padding)

    when (node.orientation) {

        Orientation.VERTICAL ->
            LazyColumn(
                modifier = mappedModifier
            ) {
                items(
                    node.items
                ) { item ->

                    UiRenderer(
                        nodes = item,
                        onAction = onAction
                    )
                }
            }

        Orientation.HORIZONTAL ->
            LazyRow(
                modifier = mappedModifier
            ) {
                items(
                    node.items
                ) { item ->

                    UiRenderer(
                        nodes = item,
                        onAction = onAction
                    )
                }
            }
    }
}