package com.dynamicui.renderer.mappers

import androidx.compose.ui.graphics.Color
import androidx.core.graphics.toColorInt

object ColorMapper {

    fun map(
        value: String?
    ): Color? {

        if (value.isNullOrBlank()) {
            return null
        }

        return try {
            Color(value.toColorInt())
        } catch (_: IllegalArgumentException) {
            null
        }
    }
}