package com.dynamicui.shared.bootstrap

object DynamicUi {

    fun createRenderer(): DynamicUiRenderer = RendererFactory.create()
}