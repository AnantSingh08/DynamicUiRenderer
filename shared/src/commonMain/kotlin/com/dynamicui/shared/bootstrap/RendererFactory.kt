package com.dynamicui.shared.bootstrap

import com.dynamicui.shared.data.mapper.FeedMapperImpl
import com.dynamicui.shared.data.mapper.UiDefinitionsMapperImpl
import com.dynamicui.shared.data.network.HttpClientProvider
import com.dynamicui.shared.data.remote.DefinitionsApi
import com.dynamicui.shared.data.remote.FeedApi
import com.dynamicui.shared.data.repository.DefinitionsRepositoryImpl
import com.dynamicui.shared.data.repository.FeedRepositoryImpl
import com.dynamicui.shared.domain.usecase.InitializeDefinitionsUseCase
import com.dynamicui.shared.domain.usecase.ResolveScreenUseCase
import com.dynamicui.shared.runtime.binding.BindingResolverImpl
import com.dynamicui.shared.runtime.registry.LayoutRegistryImpl
import com.dynamicui.shared.runtime.registry.StyleRegistryImpl
import com.dynamicui.shared.runtime.resolver.UiRuntimeResolverImpl

internal object RendererFactory {

    fun create(): DynamicUiRenderer {

        val client = HttpClientProvider.client

        val feedApi = FeedApi(client)
        val definitionsApi = DefinitionsApi(client)

        val feedMapper = FeedMapperImpl()
        val definitionsMapper = UiDefinitionsMapperImpl()

        val feedRepository = FeedRepositoryImpl(
            feedApi = feedApi,
            feedMapper = feedMapper
        )

        val definitionsRepository = DefinitionsRepositoryImpl(
            api = definitionsApi,
            mapper = definitionsMapper
        )

        val layoutRegistry = LayoutRegistryImpl()
        val styleRegistry = StyleRegistryImpl()

        val bindingResolver = BindingResolverImpl()

        val runtimeResolver = UiRuntimeResolverImpl(
            bindingResolver = bindingResolver,
            styleRegistry = styleRegistry
        )

        val initializeDefinitionsUseCase =
            InitializeDefinitionsUseCase(
                definitionsRepository = definitionsRepository,
                layoutRegistry = layoutRegistry,
                styleRegistry = styleRegistry
            )

        val resolveScreenUseCase =
            ResolveScreenUseCase(
                feedRepository = feedRepository,
                layoutRegistry = layoutRegistry,
                runtimeResolver = runtimeResolver
            )

        return DynamicUiRenderer(
            initializeDefinitionsUseCase = initializeDefinitionsUseCase,
            resolveScreenUseCase = resolveScreenUseCase
        )
    }
}