package com.leggomymeggos.marvelcompose.data

import com.leggomymeggos.marvelcompose.network.MarvelService
import com.leggomymeggos.marvelcompose.network.data.CharacterResponse
import javax.inject.Inject

class CharacterRepository @Inject constructor(private val marvelService: MarvelService) {

    suspend fun fetchCharacters(): List<CharacterResponse> {
        return marvelService.getCharacters().let {
            if (!it.isSuccessful) {
                emptyList()
            } else {
                it.body()?.data?.results ?: emptyList()
            }
        }
    }
}
