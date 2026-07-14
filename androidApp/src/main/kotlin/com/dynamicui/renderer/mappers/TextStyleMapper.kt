package com.dynamicui.presentation.renderer.modifier

import androidx.compose.material3.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.dynamicui.renderer.mappers.ColorMapper
import com.dynamicui.shared.model.Style

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
        value: String?
    ): FontWeight? {

        return when (value?.lowercase()) {

            "thin" -> FontWeight.Thin

            "extralight" -> FontWeight.ExtraLight

            "light" -> FontWeight.Light

            "normal" -> FontWeight.Normal

            "medium" -> FontWeight.Medium

            "semibold" -> FontWeight.SemiBold

            "bold" -> FontWeight.Bold

            "extrabold" -> FontWeight.ExtraBold

            "black" -> FontWeight.Black

            else -> null
        }
    }
}