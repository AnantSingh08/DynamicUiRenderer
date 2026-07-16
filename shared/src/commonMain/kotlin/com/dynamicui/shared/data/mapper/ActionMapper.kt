package com.dynamicui.shared.data.mapper

import com.dynamicui.shared.data.dto.action.NavigateActionDto
import com.dynamicui.shared.data.dto.action.ToastActionDto
import com.dynamicui.shared.data.dto.action.UiActionDto
import com.dynamicui.shared.model.action.NavigateAction
import com.dynamicui.shared.model.action.ToastAction
import com.dynamicui.shared.model.action.UiAction

object ActionMapper {

    fun map(dto: UiActionDto?): UiAction? {
        return when (dto) {
            null -> null
            is NavigateActionDto -> NavigateAction(
                destination = dto.destination,
                params = dto.params,
            )
            is ToastActionDto -> ToastAction(
                message = dto.message,
            )
        }
    }
}
