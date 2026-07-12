package com.dynamicui.shared.domain.model

import com.dynamicui.shared.model.UiAction
import com.dynamicui.shared.domain.value.BindingKey
import com.dynamicui.shared.domain.value.ComponentId
import com.dynamicui.shared.domain.value.LayoutId
import com.dynamicui.shared.domain.value.UiValue

data class FeedItem(

    val id: ComponentId,

    val layoutId: LayoutId,

    val data: Map<BindingKey, UiValue>,

    val action: UiAction? = null
)