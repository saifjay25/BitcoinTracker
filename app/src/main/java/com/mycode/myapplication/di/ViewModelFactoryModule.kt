package com.mycode.myapplication.di

import androidx.lifecycle.ViewModelProvider
import com.mycode.myapplication.viewModels.ViewModelProviderFactory
import dagger.Binds
import dagger.Module

@Module
abstract class ViewModelFactoryModule {

    //providing instance of the viewmodel provider factory
    @Binds
    internal abstract fun bindViewModelFactory(viewModelsProvidersFactory: ViewModelProviderFactory) : ViewModelProvider.Factory
}