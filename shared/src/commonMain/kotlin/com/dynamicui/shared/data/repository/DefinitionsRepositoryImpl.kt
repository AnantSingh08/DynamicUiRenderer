package com.dynamicui.shared.data.repository

import com.dynamicui.shared.data.mapper.UiDefinitionsMapper
import com.dynamicui.shared.data.remote.DefinitionsApi
import com.dynamicui.shared.domain.model.UiDefinitions
import com.dynamicui.shared.domain.repository.DefinitionsRepository

class DefinitionsRepositoryImpl(
    private val api: DefinitionsApi,
    private val mapper: UiDefinitionsMapper
) : DefinitionsRepository {

    override suspend fun getDefinitions(): UiDefinitions {

        val dto = api.getDefinitions()

        return mapper.map(dto)
    }
}