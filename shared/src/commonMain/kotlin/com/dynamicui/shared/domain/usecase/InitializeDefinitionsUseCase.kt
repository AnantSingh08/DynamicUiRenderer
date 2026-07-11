package com.dynamicui.shared.domain.usecase

import com.dynamicui.shared.domain.model.UiDefinitions
import com.dynamicui.shared.domain.repository.DefinitionsRepository
import com.dynamicui.shared.runtime.registry.LayoutRegistry
import com.dynamicui.shared.runtime.registry.StyleRegistry

class InitializeDefinitionsUseCase(
    private val definitionsRepository: DefinitionsRepository,
    private val layoutRegistry: LayoutRegistry,
    private val styleRegistry: StyleRegistry
) {

    suspend operator fun invoke() {

        val definitions: UiDefinitions =
            definitionsRepository.getDefinitions()

        layoutRegistry.registerLayouts(
            definitions.layouts
        )

        styleRegistry.registerStyles(
            definitions.styles
        )
    }
}
