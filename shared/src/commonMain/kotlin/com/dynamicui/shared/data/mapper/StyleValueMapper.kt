package com.dynamicui.shared.data.mapper

import com.dynamicui.shared.model.style.Alignment
import com.dynamicui.shared.model.style.CornerRadius
import com.dynamicui.shared.model.style.Dimension
import com.dynamicui.shared.model.style.EdgeInsets
import com.dynamicui.shared.model.style.FontWeight

internal object StyleValueMapper {

    fun toEdgeInsets(value: String?): EdgeInsets? {

        val values = parseFourValues(value) ?: return null

        return EdgeInsets(
            top = values[0],
            right = values[1],
            bottom = values[2],
            left = values[3]
        )
    }

    fun toCornerRadius(value: String?): CornerRadius? {

        val values = parseFourValues(value) ?: return null

        return CornerRadius(
            topStart = values[0],
            topEnd = values[1],
            bottomEnd = values[2],
            bottomStart = values[3]
        )
    }

    private fun parseFourValues(
        value: String?
    ): List<Int>? {

        if (value.isNullOrBlank()) {
            return null
        }

        val values = value
            .split(",")
            .map { it.trim() }
            .map {
                it.toIntOrNull()
                    ?: throw IllegalArgumentException(
                        "Invalid integer value '$it' in '$value'"
                    )
            }

        require(values.size == 4) {
            "Expected exactly 4 comma-separated values but found ${values.size} in '$value'."
        }

        return values
    }

    fun toDimension(value: String?): Dimension? {

        val normalized = value?.trim()?.lowercase()

        return when (normalized) {

            "fill" -> Dimension.Fill

            "wrap" -> Dimension.Wrap

            null, "" -> null

            else -> normalized.toIntOrNull()?.let {
                Dimension.Fixed(it)
            } ?: throw IllegalArgumentException(
                "Unknown dimension '$value'"
            )
        }
    }

    fun toAlignment(value: String?): Alignment? {

        return when (value?.trim()?.lowercase()) {

            "start" -> Alignment.START

            "center" -> Alignment.CENTER

            "end" -> Alignment.END

            null, "" -> null

            else -> throw IllegalArgumentException(
                "Unknown alignment '$value'"
            )
        }
    }

    fun toFontWeight(value: String?): FontWeight? {

        return when (value?.trim()?.lowercase()) {

            "normal" -> FontWeight.NORMAL

            "medium" -> FontWeight.MEDIUM

            "semibold" -> FontWeight.SEMIBOLD

            "bold" -> FontWeight.BOLD

            null, "" -> null

            else -> throw IllegalArgumentException(
                "Unknown font weight '$value'"
            )
        }
    }
}