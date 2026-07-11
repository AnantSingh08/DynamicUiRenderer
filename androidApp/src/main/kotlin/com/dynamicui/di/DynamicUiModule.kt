package com.dynamicui.di

import com.dynamicui.shared.bootstrap.DynamicUi
import com.dynamicui.shared.bootstrap.DynamicUiRenderer
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DynamicUiModule {

    @Provides
    @Singleton
    fun provideRenderer(): DynamicUiRenderer {
        return DynamicUi.createRenderer()
    }
}