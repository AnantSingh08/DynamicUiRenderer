package com.dynamicui.shared.model.style

sealed interface Dimension {
    data object Fill : Dimension
    data object Wrap : Dimension
    data class Fixed(val value: Int) : Dimension
}