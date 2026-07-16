package com.dynamicui.renderer.mappers

import androidx.compose.ui.Alignment
import com.dynamicui.shared.model.style.Alignment as UiAlignment

object AlignmentMapper {

    fun horizontal(
        alignment: UiAlignment?
    ): Alignment.Horizontal {

        return when (alignment) {

            UiAlignment.START -> Alignment.Start

            UiAlignment.CENTER -> Alignment.CenterHorizontally

            UiAlignment.END -> Alignment.End

            null -> Alignment.Start
        }
    }

    fun vertical(
        alignment: UiAlignment?
    ): Alignment.Vertical {

        return when (alignment) {

            UiAlignment.START -> Alignment.Top

            UiAlignment.CENTER -> Alignment.CenterVertically

            UiAlignment.END -> Alignment.Bottom

            null -> Alignment.Top
        }
    }
}