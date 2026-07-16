package com.dynamicui.renderer.extensions

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.dynamicui.shared.model.style.EdgeInsets

fun EdgeInsets.toPaddingValues(): PaddingValues =
    PaddingValues(
        start = left.dp,
        top = top.dp,
        end = right.dp,
        bottom = bottom.dp
    )

fun Modifier.padding(
    edgeInsets: EdgeInsets?
): Modifier {

    return if (edgeInsets == null) {
        this
    } else {
        this.padding(edgeInsets.toPaddingValues())
    }
}