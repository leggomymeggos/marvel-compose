package com.leggomymeggos.marvelcompose.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.leggomymeggos.marvelcompose.data.Character

class CharacterListViewModel : ViewModel() {
    private val characters = mutableListOf(
        Character(id = 1, name = "Spider-Man", favorite = true),
        Character(id = 2, name = "Venom", favorite = false),
        Character(id = 3, name = "Iron Man", favorite = false),
        Character(id = 4, name = "Thor", favorite = true),
        Character(id = 5, name = "Scarlet Witch", favorite = true),
        Character(id = 6, name = "Photon", favorite = false),
    )
    private val _data = MutableLiveData(characters)
    val data: LiveData<List<Character>> = Transformations.map(_data) {
        it.map { character -> character }
    }

    fun toggleFavorite(characterId: Int) {
        val index = characters.indexOfFirst { it.id == characterId }
        val characterToUpdate = characters.first { it.id == characterId }

        characters.removeAt(index)
        characters.add(index, characterToUpdate.copy(favorite = !characterToUpdate.favorite))

        _data.postValue(characters)
    }
}
