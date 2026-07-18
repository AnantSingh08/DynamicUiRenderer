package com.dynamicui.presentation.renderer.modifier

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.dynamicui.shared.model.style.Dimension
import com.dynamicui.shared.model.style.Style

fun Modifier.applyStyle(style: Style?): Modifier {
    if (style == null) {
        return this
    }

    var result = this

    style.width?.let { width ->

        result = when (width) {

            Dimension.Fill ->
                result.fillMaxWidth()

            Dimension.Wrap ->
                result.wrapContentWidth()

            is Dimension.Fixed ->
                result.width(width.value.dp)
        }
    }

    style.height?.let { height ->

        result = when (height) {

            Dimension.Fill ->
                result.fillMaxHeight()

            Dimension.Wrap ->
                result.wrapContentHeight()

            is Dimension.Fixed ->
                result.height(height.value.dp)
        }
    }

    style.margin?.let { margin ->

        result = result.padding(
            PaddingValues(
                start = margin.left.dp,
                top = margin.top.dp,
                end = margin.right.dp,
                bottom = margin.bottom.dp
            )
        )
    }

    return result
}