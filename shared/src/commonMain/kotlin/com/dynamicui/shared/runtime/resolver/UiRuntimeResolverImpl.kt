package com.dynamicui.shared.runtime.resolver

import com.dynamicui.shared.definition.CardDefinition
import com.dynamicui.shared.definition.ComponentDefinition
import com.dynamicui.shared.definition.ImageDefinition
import com.dynamicui.shared.definition.ListDefinition
import com.dynamicui.shared.definition.StackDefinition
import com.dynamicui.shared.definition.TextDefinition
import com.dynamicui.shared.domain.model.FeedItem
import com.dynamicui.shared.domain.model.LayoutDefinition
import com.dynamicui.shared.domain.value.BindingKey
import com.dynamicui.shared.domain.value.StyleId
import com.dynamicui.shared.domain.value.UiValue
import com.dynamicui.shared.domain.value.asString
import com.dynamicui.shared.runtime.binding.BindingResolver
import com.dynamicui.shared.model.CardNode
import com.dynamicui.shared.model.ImageNode
import com.dynamicui.shared.model.ListNode
import com.dynamicui.shared.model.StackNode
import com.dynamicui.shared.model.TextNode
import com.dynamicui.shared.model.UiNode
import com.dynamicui.shared.runtime.registry.StyleRegistry
import com.dynamicui.shared.style.Style

class UiRuntimeResolverImpl(
    private val bindingResolver: BindingResolver,
    private val styleRegistry: StyleRegistry
) : UiRuntimeResolver {

    override fun resolve(
        layout: LayoutDefinition,
        feedItem: FeedItem
    ): UiNode {

        return resolveComponent(
            component = layout.root,
            feedItem = feedItem
        )
    }

    private fun resolveComponent(
        component: ComponentDefinition,
        feedItem: FeedItem
    ): UiNode {

        return when (component) {

            is CardDefinition ->
                resolveCard(
                    component,
                    feedItem
                )

            is StackDefinition ->
                resolveStack(
                    component,
                    feedItem
                )

            is ListDefinition ->
                resolveList(
                    component,
                    feedItem
                )

            is TextDefinition ->
                resolveText(
                    component,
                    feedItem
                )

            is ImageDefinition ->
                resolveImage(
                    component,
                    feedItem
                )
        }
    }

    private fun resolveCard(
        definition: CardDefinition,
        feedItem: FeedItem
    ): CardNode {

        return CardNode(

            id = definition.id,

            style = resolveStyle(
                definition.styleId
            ),

            action = definition.action,

            children = resolveChildren(
                definition.children,
                feedItem
            )
        )
    }

    private fun resolveStack(
        definition: StackDefinition,
        feedItem: FeedItem
    ): StackNode {

        return StackNode(

            id = definition.id,

            orientation = definition.orientation,

            style = resolveStyle(
                definition.styleId
            ),

            action = definition.action,

            children = resolveChildren(
                definition.children,
                feedItem
            )
        )
    }

    private fun resolveList(
        definition: ListDefinition,
        feedItem: FeedItem
    ): ListNode {

        return ListNode(

            id = definition.id,

            orientation = definition.orientation,

            style = resolveStyle(
                definition.styleId
            ),

            action = definition.action,

            children = resolveChildren(
                definition.children,
                feedItem
            )
        )
    }

    private fun resolveText(
        definition: TextDefinition,
        feedItem: FeedItem
    ): TextNode {

        val resolvedText =
            definition.text
                ?: resolveBinding(
                    definition.binding,
                    feedItem
                )?.asString()
                ?: ""

        return TextNode(
            id = definition.id,
            style = resolveStyle(
                definition.styleId
            ),
            action = definition.action,
            text = resolvedText
        )
    }

    private fun resolveImage(
        definition: ImageDefinition,
        feedItem: FeedItem
    ): ImageNode {

        val resolvedUrl =
            definition.url
                ?: resolveBinding(
                    definition.binding,
                    feedItem
                )?.asString()
                ?: ""

        return ImageNode(
            id = definition.id,
            style = resolveStyle(
                definition.styleId
            ),
            action = definition.action,
            url = resolvedUrl
        )
    }

    private fun resolveStyle(
        styleId: StyleId?
    ): Style? {

        if (styleId == null) {
            return null
        }

        return styleRegistry.getStyle(styleId)
    }

    private fun resolveBinding(
        binding: BindingKey?,
        feedItem: FeedItem
    ): UiValue? {

        return bindingResolver.resolve(
            binding = binding,
            data = feedItem.data
        )
    }

    private fun resolveChildren(
        children: List<ComponentDefinition>,
        feedItem: FeedItem
    ): List<UiNode> {

        return children.map { child ->

            resolveComponent(
                component = child,
                feedItem = feedItem
            )
        }
    }
}