package com.dynamicui.shared.data.mapper

import com.dynamicui.shared.data.dto.definitions.CardDefinitionDto
import com.dynamicui.shared.data.dto.definitions.ComponentDefinitionDto
import com.dynamicui.shared.data.dto.definitions.ImageDefinitionDto
import com.dynamicui.shared.data.dto.definitions.LayoutDefinitionDto
import com.dynamicui.shared.data.dto.definitions.ListDefinitionDto
import com.dynamicui.shared.data.dto.definitions.StackDefinitionDto
import com.dynamicui.shared.data.dto.definitions.StyleDefinitionDto
import com.dynamicui.shared.data.dto.definitions.TextDefinitionDto
import com.dynamicui.shared.data.dto.definitions.UiDefinitionsDto
import com.dynamicui.shared.definition.CardDefinition
import com.dynamicui.shared.definition.ComponentDefinition
import com.dynamicui.shared.definition.ImageDefinition
import com.dynamicui.shared.definition.ListDefinition
import com.dynamicui.shared.definition.StackDefinition
import com.dynamicui.shared.definition.TextDefinition
import com.dynamicui.shared.domain.model.LayoutDefinition
import com.dynamicui.shared.domain.model.UiDefinitions
import com.dynamicui.shared.domain.value.BindingKey
import com.dynamicui.shared.domain.value.ComponentId
import com.dynamicui.shared.domain.value.LayoutId
import com.dynamicui.shared.domain.value.StyleId
import com.dynamicui.shared.model.Orientation
import com.dynamicui.shared.model.Style

class UiDefinitionsMapperImpl : UiDefinitionsMapper {

    override fun map(input: UiDefinitionsDto): UiDefinitions {
        return UiDefinitions(
            layouts = input.layouts.associate { dto ->
                LayoutId(dto.id) to mapLayout(dto)
            },
            styles = input.styles.associate { dto ->
                StyleId(dto.id) to mapStyle(dto)
            },
        )
    }

    private fun mapLayout(dto: LayoutDefinitionDto): LayoutDefinition {
        return LayoutDefinition(
            id = LayoutId(dto.id),
            root = mapComponent(dto.root),
        )
    }

    private fun mapStyle(dto: StyleDefinitionDto): Style {
        return Style(
            width = StyleValueMapper.toDimension(dto.width),
            height = StyleValueMapper.toDimension(dto.height),
            spacing = dto.spacing,
            backgroundColor = dto.backgroundColor,
            textColor = dto.textColor,
            fontSize = dto.fontSize,
            fontWeight = dto.fontWeight,
            alignment = dto.alignment,

            padding = StyleValueMapper.toEdgeInsets(dto.padding),
            margin = StyleValueMapper.toEdgeInsets(dto.margin),
            cornerRadius = StyleValueMapper.toCornerRadius(dto.cornerRadius),
        )
    }

    private fun mapComponent(dto: ComponentDefinitionDto): ComponentDefinition {
        return when (dto) {
            is TextDefinitionDto -> TextDefinition(
                id = ComponentId(dto.id),
                styleId = dto.styleId?.let(::StyleId),
                action = ActionMapper.map(dto.action),
                text = dto.text,
                binding = dto.binding?.let(::BindingKey),
            )

            is ImageDefinitionDto -> ImageDefinition(
                id = ComponentId(dto.id),
                styleId = dto.styleId?.let(::StyleId),
                action = ActionMapper.map(dto.action),
                url = dto.url,
                binding = dto.binding?.let(::BindingKey),
            )

            is StackDefinitionDto -> StackDefinition(
                id = ComponentId(dto.id),
                orientation = mapOrientation(dto.orientation),
                styleId = dto.styleId?.let(::StyleId),
                action = ActionMapper.map(dto.action),
                children = dto.children.map(::mapComponent),
            )

            is CardDefinitionDto -> CardDefinition(
                id = ComponentId(dto.id),
                styleId = dto.styleId?.let(::StyleId),
                action = ActionMapper.map(dto.action),
                children = dto.children.map(::mapComponent),
            )

            is ListDefinitionDto -> ListDefinition(
                id = ComponentId(dto.id),
                orientation = mapOrientation(dto.orientation),
                binding = BindingKey(dto.binding),
                styleId = dto.styleId?.let(::StyleId),
                action = ActionMapper.map(dto.action),
                children = dto.children.map(::mapComponent),
            )
        }
    }

    private fun mapOrientation(value: String): Orientation {
        return when (value.lowercase()) {
            "horizontal" -> Orientation.HORIZONTAL
            else -> Orientation.VERTICAL
        }
    }
}
