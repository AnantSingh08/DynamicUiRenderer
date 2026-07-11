package com.dynamicui.shared.data.network

import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

object HttpClientProvider {
    val client = HttpClient(OkHttp) {
        install(ContentNegotiation) {
            json(
                Json {
                    serializersModule = dynamicUiModule
                    ignoreUnknownKeys = true
                    classDiscriminator = "type"
                    explicitNulls = false
                }
            )
        }
    }
}