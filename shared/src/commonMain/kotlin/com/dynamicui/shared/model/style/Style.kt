package com.dynamicui.shared.model.style

data class Style(
    val width: Dimension? = null,
    val height: Dimension? = null,
    val margin: EdgeInsets? = null,
    val padding: EdgeInsets? = null,
    val spacing: Int? = null,
    val backgroundColor: String? = null,
    val textColor: String? = null,
    val fontSize: Int? = null,
    val fontWeight: FontWeight? = null,
    val cornerRadius: CornerRadius? = null,
    val alignment: Alignment? = null
)