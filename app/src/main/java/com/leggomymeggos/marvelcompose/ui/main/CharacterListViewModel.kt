package com.leggomymeggos.marvelcompose.ui.main

import com.airbnb.mvrx.MavericksState
import com.airbnb.mvrx.MavericksViewModel
import com.airbnb.mvrx.MavericksViewModelFactory
import com.airbnb.mvrx.hilt.AssistedViewModelFactory
import com.airbnb.mvrx.hilt.hiltMavericksViewModelFactory
import com.leggomymeggos.marvelcompose.data.Character
import com.leggomymeggos.marvelcompose.data.CharacterUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.launch

class CharacterListViewModel @AssistedInject constructor(
    @Assisted initialState: State,
    private val characterUseCase: CharacterUseCase
) : MavericksViewModel<State>(initialState) {

    @AssistedFactory
    interface Factory : AssistedViewModelFactory<CharacterListViewModel, State> {
        override fun create(state: State): CharacterListViewModel
    }

    companion object : MavericksViewModelFactory<CharacterListViewModel, State> by hiltMavericksViewModelFactory()

    init {
        viewModelScope.launch {
            val characters = characterUseCase.getCharacters()

            setState {
                copy(characterList = characters)
            }
        }
    }
}

data class State(val characterList: List<Character> = listOf()) : MavericksState
