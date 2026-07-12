package com.dynamicui.presentation.renderer.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.dynamicui.presentation.renderer.modifier.ModifierMapper
import com.dynamicui.presentation.renderer.modifier.TextStyleMapper
import com.dynamicui.shared.model.TextNode
import com.dynamicui.shared.model.UiAction

@Composable
fun TextRenderer(
    node: TextNode,
    modifier: Modifier = Modifier,
    onAction: (UiAction) -> Unit
) {

    Text(
        text = node.text,
        modifier = ModifierMapper.map(
            style = node.style,
            modifier = modifier
        ),
        style = TextStyleMapper.map(
            node.style
        )
    )
}