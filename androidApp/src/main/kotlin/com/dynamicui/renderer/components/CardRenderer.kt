package com.dynamicui.renderer.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.dynamicui.presentation.renderer.modifier.ModifierMapper
import com.dynamicui.renderer.UiRenderer
import com.dynamicui.renderer.action.clickableAction
import com.dynamicui.renderer.extensions.padding
import com.dynamicui.renderer.mappers.ColorMapper
import com.dynamicui.renderer.mappers.ShapeMapper
import com.dynamicui.shared.model.node.CardNode
import com.dynamicui.shared.model.action.UiAction

@Composable
fun CardRenderer(
    node: CardNode,
    modifier: Modifier = Modifier,
    onAction: (UiAction) -> Unit
) {
    Card(
        modifier = ModifierMapper
            .map(
                style = node.style,
                modifier = modifier
            )
            .clickableAction(
                node.action,
                onAction
            ),

        shape = ShapeMapper.map(
            node.style?.cornerRadius
        ),

        colors = CardDefaults.cardColors(
            containerColor = ColorMapper.map(
                node.style?.backgroundColor
            ) ?: colorScheme.surface
        )
    ) {
        val contentModifier = Modifier
            .padding(node.style?.padding)

        Column(
            modifier = contentModifier
        ) {
            UiRenderer(
                nodes = node.children,
                onAction = onAction
            )
        }
    }
}