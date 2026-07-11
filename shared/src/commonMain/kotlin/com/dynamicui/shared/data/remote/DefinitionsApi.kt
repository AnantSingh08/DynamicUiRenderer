package com.dynamicui.shared.data.remote

import com.dynamicui.shared.data.dto.definitions.UiDefinitionsDto
import com.dynamicui.shared.data.network.ApiConfig
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class DefinitionsApi(
    private val client: HttpClient
) {

    suspend fun getDefinitions(): UiDefinitionsDto {
        return client
            .get(ApiConfig.DEFINITIONS)
            .body()
    }
}