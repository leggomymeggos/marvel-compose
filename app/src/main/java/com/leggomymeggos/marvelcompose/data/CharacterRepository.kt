package com.leggomymeggos.marvelcompose.data

import com.leggomymeggos.marvelcompose.network.MarvelService
import com.leggomymeggos.marvelcompose.network.data.CharacterResponse
import javax.inject.Inject

class CharacterRepository @Inject constructor(private val marvelService: MarvelService) {

    companion object {
        const val DEFAULT_PAGE_SIZE = 100
    }

    suspend fun fetchCharacters(pageNumber: Int = 1): List<CharacterResponse> {
        val offset = DEFAULT_PAGE_SIZE * (pageNumber - 1)
        return marvelService.getCharacters(offset= offset).let {
            if (!it.isSuccessful) {
                // TODO error state
                emptyList()
            } else {
                it.body()?.data?.results ?: emptyList()
            }
        }
    }
}
