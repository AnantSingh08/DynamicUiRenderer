package com.dynamicui.renderer.mappers

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp
import com.dynamicui.shared.model.Style

object ShapeMapper {

    fun map(
        style: Style?
    ): Shape {

        val radius = style?.cornerRadius
            ?: return RectangleShape

        return RoundedCornerShape(
            topStart = radius.topStart.dp,
            topEnd = radius.topEnd.dp,
            bottomEnd = radius.bottomEnd.dp,
            bottomStart = radius.bottomStart.dp
        )
    }
}