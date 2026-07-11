package com.dynamicui.shared.model

import com.dynamicui.shared.domain.model.UiAction
import com.dynamicui.shared.domain.value.ComponentId
import com.dynamicui.shared.style.Style

data class ListNode(

    override val id: ComponentId,

    val orientation: Orientation,

    override val style: Style?,

    override val action: UiAction?,

    val children: List<UiNode>

) : UiNode