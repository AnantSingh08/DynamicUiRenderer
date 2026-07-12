package com.dynamicui.presentation.renderer.modifier

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.dynamicui.shared.model.Style

object ModifierMapper {

    fun map(
        style: Style?,
        modifier: Modifier = Modifier
    ): Modifier {

        if (style == null) {
            return modifier
        }

        var result = modifier

        style.width?.let {
            result = result.width(it.dp)
        }

        style.height?.let {
            result = result.height(it.dp)
        }

        style.padding?.let { padding ->

            result = result.padding(
                PaddingValues(
                    start = padding.left.dp,
                    top = padding.top.dp,
                    end = padding.right.dp,
                    bottom = padding.bottom.dp
                )
            )
        }

        return result
    }
}