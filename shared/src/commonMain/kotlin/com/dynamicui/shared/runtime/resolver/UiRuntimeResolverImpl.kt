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
import com.dynamicui.shared.domain.value.ListValue
import com.dynamicui.shared.domain.value.StyleId
import com.dynamicui.shared.domain.value.UiValue
import com.dynamicui.shared.domain.value.asString
import com.dynamicui.shared.model.CardNode
import com.dynamicui.shared.model.ImageNode
import com.dynamicui.shared.model.ListNode
import com.dynamicui.shared.model.StackNode
import com.dynamicui.shared.model.Style
import com.dynamicui.shared.model.TextNode
import com.dynamicui.shared.model.UiNode
import com.dynamicui.shared.runtime.binding.BindingContext
import com.dynamicui.shared.runtime.binding.BindingResolver
import com.dynamicui.shared.runtime.registry.StyleRegistry

class UiRuntimeResolverImpl(
    private val bindingResolver: BindingResolver,
    private val styleRegistry: StyleRegistry
) : UiRuntimeResolver {

    override fun resolve(
        layout: LayoutDefinition,
        feedItem: FeedItem
    ): UiNode {

        val context = BindingContext(
            data = feedItem.data
        )

        return resolveComponent(
            component = layout.root,
            context = context
        )
    }

    private fun resolveComponent(
        component: ComponentDefinition,
        context: BindingContext
    ): UiNode {

        return when (component) {

            is CardDefinition ->
                resolveCard(
                    component,
                    context
                )

            is StackDefinition ->
                resolveStack(
                    component,
                    context
                )

            is ListDefinition ->
                resolveList(
                    component,
                    context
                )

            is TextDefinition ->
                resolveText(
                    component,
                    context
                )

            is ImageDefinition ->
                resolveImage(
                    component,
                    context
                )
        }
    }

    private fun resolveCard(
        definition: CardDefinition,
        context: BindingContext
    ): CardNode {

        return CardNode(

            id = definition.id,

            style = resolveStyle(
                definition.styleId
            ),

            action = definition.action,

            children = resolveChildren(
                definition.children,
                context
            )
        )
    }

    private fun resolveStack(
        definition: StackDefinition,
        context: BindingContext
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
                context
            )
        )
    }

    private fun resolveList(
        definition: ListDefinition,
        context: BindingContext
    ): ListNode {

        val listValue = resolveBinding(
            definition.binding,
            context
        ) as? ListValue ?: ListValue(emptyList())

        val items = listValue.values.map { objectValue ->

            val itemContext = BindingContext(
                data = objectValue.values
            )

            resolveChildren(
                children = definition.children,
                context = itemContext
            )
        }

        return ListNode(
            id = definition.id,
            orientation = definition.orientation,
            style = resolveStyle(
                definition.styleId
            ),
            action = definition.action,
            items = items
        )
    }

    private fun resolveText(
        definition: TextDefinition,
        context: BindingContext
    ): TextNode {

        val resolvedText =
            definition.text
                ?: resolveBinding(
                    definition.binding,
                    context
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
        context: BindingContext
    ): ImageNode {

        val resolvedUrl =
            definition.url
                ?: resolveBinding(
                    definition.binding,
                    context
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
        context: BindingContext
    ): UiValue? {

        return bindingResolver.resolve(
            binding = binding,
            context = context
        )
    }

    private fun resolveChildren(
        children: List<ComponentDefinition>,
        context: BindingContext
    ): List<UiNode> {

        return children.map { child ->

            resolveComponent(
                component = child,
                context = context
            )
        }
    }
}