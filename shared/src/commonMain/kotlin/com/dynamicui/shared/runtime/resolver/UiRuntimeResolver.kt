package com.dynamicui.shared.runtime.resolver

import com.dynamicui.shared.domain.model.FeedItem
import com.dynamicui.shared.domain.model.LayoutDefinition
import com.dynamicui.shared.model.node.UiNode

interface UiRuntimeResolver {

    fun resolve(
        layout: LayoutDefinition,
        feedItem: FeedItem
    ): UiNode
}