package com.dynamicui.renderer.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import coil3.compose.AsyncImage
import com.dynamicui.presentation.renderer.modifier.ModifierMapper
import com.dynamicui.renderer.action.clickableAction
import com.dynamicui.renderer.mappers.ShapeMapper
import com.dynamicui.shared.model.ImageNode
import com.dynamicui.shared.model.UiAction

@Composable
fun ImageRenderer(
    node: ImageNode,
    modifier: Modifier = Modifier,
    onAction: (UiAction) -> Unit
) {

    val mappedModifier = ModifierMapper
        .map(
            style = node.style,
            modifier = modifier
        )
        .clip(
            ShapeMapper.map(
                node.style?.cornerRadius
            )
        )
        .clickableAction(
            action = node.action,
            onAction = onAction
        )

    AsyncImage(
        model = node.url,
        contentDescription = null,
        modifier = mappedModifier,
        contentScale = ContentScale.Crop
    )
}