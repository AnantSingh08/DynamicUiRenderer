package com.dynamicui.renderer.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.dynamicui.shared.model.UiAction
import com.dynamicui.shared.model.ListNode

@Composable
fun ListRenderer(
    node: ListNode,
    modifier: Modifier = Modifier,
    onAction: (UiAction) -> Unit
) {
    Text("Image renderer coming soon")
}