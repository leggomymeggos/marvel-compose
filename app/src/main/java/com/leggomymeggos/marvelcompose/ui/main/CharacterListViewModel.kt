package com.leggomymeggos.marvelcompose.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

open class CharacterListViewModel @AssistedInject constructor(
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

    open fun dispatch(action: CharacterAction) {
        when (action) {
            is CharacterAction.LoadNextPage -> loadNextPage()
        }
    }

    private fun loadNextPage() {
        withState {
            viewModelScope.launch {
                val additionalCharacters = characterUseCase.getCharacters(it.currentPage + 1)
                val newCharacters = it.characterList.toMutableList()
                newCharacters.addAll(additionalCharacters)
                setState {
                    copy(
                        characterList = newCharacters,
                        currentPage = it.currentPage + 1
                    )
                }
            }
        }
    }
}

sealed class CharacterAction {
    object LoadNextPage : CharacterAction()
}

@HiltViewModel
class NonMavericksCharacterListViewModel @Inject constructor(
    private val characterUseCase: CharacterUseCase
) : ViewModel() {

    private val _state: MutableStateFlow<State> = MutableStateFlow(State())
    val state: StateFlow<State> = _state

    init {
        viewModelScope.launch {
            val characters = characterUseCase.getCharacters()
            _state.emit(State(characters))
        }
    }

    fun dispatch(action: CharacterAction) {
        when (action) {
            is CharacterAction.LoadNextPage -> loadNextPage()
        }
    }

    private fun loadNextPage() {
        viewModelScope.launch {
            val additionalCharacters = characterUseCase.getCharacters(_state.value.currentPage + 1)
            val newCharacters = _state.value.characterList.toMutableList()
            newCharacters.addAll(additionalCharacters)
            _state.emit(
                State(characterList = newCharacters, currentPage = _state.value.currentPage + 1)
            )
        }
    }
}

data class State(
    val characterList: List<Character> = listOf(),
    val currentPage: Int = 1
) : MavericksState
