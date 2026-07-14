package com.dynamicui.shared.runtime.binding

import com.dynamicui.shared.domain.value.BindingKey
import com.dynamicui.shared.domain.value.UiValue

class BindingResolverImpl : BindingResolver {

    override fun resolve(
        binding: BindingKey?,
        context: BindingContext
    ): UiValue? {

        if (binding == null)
            return null

        return context.data[binding]
    }
}