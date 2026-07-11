package com.dynamicui.shared.domain.repository

import com.dynamicui.shared.domain.model.UiDefinitions

interface DefinitionsRepository {
    suspend fun getDefinitions(): UiDefinitions
}