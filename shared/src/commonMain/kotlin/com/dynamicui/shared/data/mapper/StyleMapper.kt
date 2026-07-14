package com.dynamicui.shared.data.mapper

import com.dynamicui.shared.model.CornerRadius
import com.dynamicui.shared.model.EdgeInsets

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
}