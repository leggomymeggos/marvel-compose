package com.leggomymeggos.marvelcompose.di

import com.airbnb.mvrx.hilt.AssistedViewModelFactory
import com.airbnb.mvrx.hilt.MavericksViewModelComponent
import com.airbnb.mvrx.hilt.ViewModelKey
import com.leggomymeggos.marvelcompose.ui.main.CharacterListViewModel
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.multibindings.IntoMap

@Module
@InstallIn(MavericksViewModelComponent::class)
interface ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(CharacterListViewModel::class)
    fun characterListViewModelFactory(factory: CharacterListViewModel.Factory): AssistedViewModelFactory<*, *>
}