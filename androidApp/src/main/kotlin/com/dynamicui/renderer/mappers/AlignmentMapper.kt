package com.dynamicui.renderer.mappers

import androidx.compose.ui.Alignment

object AlignmentMapper {

    fun horizontal(alignment: String?): Alignment.Horizontal =
        when (alignment?.lowercase()) {
            "center" -> Alignment.CenterHorizontally
            "end" -> Alignment.End
            else -> Alignment.Start
        }

    fun vertical(alignment: String?): Alignment.Vertical =
        when (alignment?.lowercase()) {
            "center" -> Alignment.CenterVertically
            "end" -> Alignment.Bottom
            else -> Alignment.Top
        }
}