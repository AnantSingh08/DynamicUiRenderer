package com.dynamicui.presentation.renderer.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.dynamicui.presentation.renderer.modifier.applyStyle
import com.dynamicui.presentation.renderer.modifier.TextStyleMapper
import com.dynamicui.renderer.action.clickableAction
import com.dynamicui.shared.model.node.TextNode
import com.dynamicui.shared.model.action.UiAction

@Composable
fun TextRenderer(
    node: TextNode,
    modifier: Modifier = Modifier,
    onAction: (UiAction) -> Unit
) {

    val mappedModifier = modifier
        .applyStyle(node.style)
        .clickableAction(
            action = node.action,
            onAction = onAction
        )

    Text(
        text = node.text,
        modifier = mappedModifier,
        style = TextStyleMapper.map(node.style)
    )
}