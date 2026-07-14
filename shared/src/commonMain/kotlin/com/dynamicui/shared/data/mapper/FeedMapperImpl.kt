package com.dynamicui.shared.data.mapper

import com.dynamicui.shared.data.dto.feed.FeedDto
import com.dynamicui.shared.data.dto.feed.FeedItemDto
import com.dynamicui.shared.domain.model.Feed
import com.dynamicui.shared.domain.model.FeedItem
import com.dynamicui.shared.domain.value.BindingKey
import com.dynamicui.shared.domain.value.BooleanValue
import com.dynamicui.shared.domain.value.ComponentId
import com.dynamicui.shared.domain.value.LayoutId
import com.dynamicui.shared.domain.value.ListValue
import com.dynamicui.shared.domain.value.NullValue
import com.dynamicui.shared.domain.value.NumberValue
import com.dynamicui.shared.domain.value.ObjectValue
import com.dynamicui.shared.domain.value.StringValue
import com.dynamicui.shared.domain.value.UiValue
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonNull
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.booleanOrNull
import kotlinx.serialization.json.doubleOrNull

class FeedMapperImpl : FeedMapper {

    override fun map(
        input: FeedDto
    ): Feed {

        return Feed(
            items = input.items.map(::mapFeedItem)
        )
    }

    private fun mapFeedItem(
        dto: FeedItemDto
    ): FeedItem {

        return FeedItem(

            id = ComponentId(dto.id),

            layoutId = LayoutId(dto.layoutId),

            data = dto.data.mapKeys {
                BindingKey(it.key)
            }.mapValues {
                mapUiValue(it.value)
            },

            action = ActionMapper.map(dto.action)
        )
    }

    private fun mapUiValue(
        element: JsonElement
    ): UiValue {

        return when (element) {

            is JsonNull -> NullValue

            is JsonPrimitive -> mapPrimitive(element)

            is JsonObject -> {

                ObjectValue(

                    element.mapKeys {

                        BindingKey(it.key)

                    }.mapValues {

                        mapUiValue(it.value)
                    }
                )
            }

            is JsonArray -> {

                ListValue(

                    element.mapNotNull {

                        mapUiValue(it) as? ObjectValue
                    }
                )
            }

            else -> NullValue
        }
    }

    private fun mapPrimitive(
        primitive: JsonPrimitive
    ): UiValue {

        primitive.booleanOrNull?.let {

            return BooleanValue(it)
        }

        primitive.doubleOrNull?.let {

            return NumberValue(it)
        }

        return StringValue(
            primitive.content
        )
    }
}