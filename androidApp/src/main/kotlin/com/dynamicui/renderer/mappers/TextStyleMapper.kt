package com.dynamicui.presentation.renderer.modifier

import androidx.compose.material3.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import com.dynamicui.shared.model.style.FontWeight as UiFontWeight
import androidx.compose.ui.unit.sp
import com.dynamicui.renderer.mappers.ColorMapper
import com.dynamicui.shared.model.style.Style

object TextStyleMapper {

    @Composable
    fun map(
        style: Style?
    ): TextStyle {

        return TextStyle(

            color = ColorMapper.map(style?.textColor)
                ?: LocalContentColor.current,

            fontSize = style
                ?.fontSize
                ?.sp
                ?: TextStyle.Default.fontSize,

            fontWeight = mapFontWeight(
                style?.fontWeight
            )
        )
    }

    private fun mapFontWeight(
        value: UiFontWeight?
    ): FontWeight? {

        return when (value) {

            UiFontWeight.THIN -> FontWeight.Thin

            UiFontWeight.EXTRA_LIGHT -> FontWeight.ExtraLight

            UiFontWeight.LIGHT -> FontWeight.Light

            UiFontWeight.NORMAL -> FontWeight.Normal

            UiFontWeight.MEDIUM -> FontWeight.Medium

            UiFontWeight.SEMIBOLD -> FontWeight.SemiBold

            UiFontWeight.BOLD -> FontWeight.Bold

            UiFontWeight.EXTRA_BOLD -> FontWeight.ExtraBold

            UiFontWeight.BLACK -> FontWeight.Black

            null -> null
        }
    }
}