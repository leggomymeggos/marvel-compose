package com.leggomymeggos.marvelcompose.data

import com.leggomymeggos.marvelcompose.network.MarvelSecurityHelper
import okhttp3.HttpUrl.Companion.toHttpUrl
import javax.inject.Inject

class CharacterUseCase @Inject constructor(private val characterRepository: CharacterRepository) {
    suspend fun getCharacters(pageNumber: Int = 1): List<Character> {
        return characterRepository.fetchCharacters(pageNumber).map { character ->
            Character(
                character.id ?: -1,
                character.name ?: "",
                character.thumbnail?.let { thumb -> thumbnailUrlCreator(thumb.path, thumb.extension) }
            )
        }
    }
}

data class Character(
    val id: Int,
    val name: String,
    val thumbnailUrl: String?
)

private fun thumbnailUrlCreator(path: String, extension: String) =
    MarvelSecurityHelper.appendKeys("$path.$extension".toHttpUrl()).toString()