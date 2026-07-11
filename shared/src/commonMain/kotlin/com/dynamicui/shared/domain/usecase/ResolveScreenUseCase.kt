package com.dynamicui.shared.domain.usecase

import com.dynamicui.shared.domain.model.Feed
import com.dynamicui.shared.domain.model.FeedItem
import com.dynamicui.shared.domain.repository.FeedRepository
import com.dynamicui.shared.model.UiNode
import com.dynamicui.shared.runtime.registry.LayoutRegistry
import com.dynamicui.shared.runtime.resolver.UiRuntimeResolver

class ResolveScreenUseCase(
    private val feedRepository: FeedRepository,
    private val layoutRegistry: LayoutRegistry,
    private val runtimeResolver: UiRuntimeResolver
) {

    suspend operator fun invoke(
        screenId: String
    ): List<UiNode> {

        val feed: Feed =
            feedRepository.getFeed(screenId)

        return feed.items.mapNotNull(::resolveFeedItem)
    }

    private fun resolveFeedItem(
        feedItem: FeedItem
    ): UiNode? {

        val layout =
            layoutRegistry.getLayout(
                feedItem.layoutId
            )
                ?: return null

        return runtimeResolver.resolve(
            layout = layout,
            feedItem = feedItem
        )
    }
}
