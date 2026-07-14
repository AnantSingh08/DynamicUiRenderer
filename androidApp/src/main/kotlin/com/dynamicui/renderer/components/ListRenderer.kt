package com.dynamicui.renderer.components

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.dynamicui.presentation.renderer.modifier.ModifierMapper
import com.dynamicui.renderer.UiRenderer
import com.dynamicui.renderer.extensions.padding
import com.dynamicui.shared.model.ListNode
import com.dynamicui.shared.model.Orientation
import com.dynamicui.shared.model.UiAction

@Composable
fun ListRenderer(
    node: ListNode,
    modifier: Modifier = Modifier,
    onAction: (UiAction) -> Unit
) {

    val mappedModifier = ModifierMapper
        .map(
            style = node.style,
            modifier = modifier
        )
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