package com.dynamicui.renderer.mappers

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp
import com.dynamicui.shared.model.style.CornerRadius

object ShapeMapper {

    fun map(
        cornerRadius: CornerRadius?
    ): Shape {

        if (cornerRadius == null) {
            return RectangleShape
        }

        return RoundedCornerShape(
            topStart = cornerRadius.topStart.dp,
            topEnd = cornerRadius.topEnd.dp,
            bottomEnd = cornerRadius.bottomEnd.dp,
            bottomStart = cornerRadius.bottomStart.dp
        )
    }
}