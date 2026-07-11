package com.dynamicui.shared.runtime.binding

import com.dynamicui.shared.domain.value.BindingKey
import com.dynamicui.shared.domain.value.UiValue

class BindingResolverImpl : BindingResolver {

    override fun resolve(
        binding: BindingKey?,
        data: Map<BindingKey, UiValue>
    ): UiValue? {

        if (binding == null)
            return null

        return data[binding]
    }
}