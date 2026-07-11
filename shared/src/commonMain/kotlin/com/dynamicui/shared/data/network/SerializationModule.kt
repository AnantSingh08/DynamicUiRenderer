package com.dynamicui.shared.data.network

import com.dynamicui.shared.data.dto.action.NavigateActionDto
import com.dynamicui.shared.data.dto.action.ToastActionDto
import com.dynamicui.shared.data.dto.action.UiActionDto
import com.dynamicui.shared.data.dto.definitions.CardDefinitionDto
import com.dynamicui.shared.data.dto.definitions.ComponentDefinitionDto
import com.dynamicui.shared.data.dto.definitions.ImageDefinitionDto
import com.dynamicui.shared.data.dto.definitions.ListDefinitionDto
import com.dynamicui.shared.data.dto.definitions.StackDefinitionDto
import com.dynamicui.shared.data.dto.definitions.TextDefinitionDto
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import kotlinx.serialization.modules.subclass

val dynamicUiModule = SerializersModule {
    polymorphic(UiActionDto::class) {
        subclass(NavigateActionDto::class)
        subclass(ToastActionDto::class)
    }

    polymorphic(ComponentDefinitionDto::class) {
        subclass(TextDefinitionDto::class)
        subclass(ImageDefinitionDto::class)
        subclass(StackDefinitionDto::class)
        subclass(CardDefinitionDto::class)
        subclass(ListDefinitionDto::class)
    }
}
