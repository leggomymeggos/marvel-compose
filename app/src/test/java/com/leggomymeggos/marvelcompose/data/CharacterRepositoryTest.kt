package com.leggomymeggos.marvelcompose.data

import com.leggomymeggos.marvelcompose.network.MarvelService
import com.leggomymeggos.marvelcompose.network.data.CharacterDataContainer
import com.leggomymeggos.marvelcompose.network.data.CharacterDataWrapper
import com.leggomymeggos.marvelcompose.network.data.CharacterResponse
import com.leggomymeggos.marvelcompose.network.data.MarvelThumbnail
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.amshove.kluent.shouldBeEqualTo
import org.amshove.kluent.shouldContain
import org.amshove.kluent.shouldHaveSize
import org.junit.Test

class CharacterRepositoryTest {

    private val marvelService = mockk<MarvelService> {
        coEvery { getCharacters(any(), any()) } returns mockk {
            every { isSuccessful } returns true
            every { body() } returns CharacterDataWrapper(
                code = null,
                status = null,
                data = null,
                attributionText = null
            )
        }
    }
    private val subject = CharacterRepository(marvelService)

    @Test
    fun `fetchCharacters gets characters from marvelService`() = runBlocking {
        subject.fetchCharacters()

        coVerify { marvelService.getCharacters(offset = 0, limit = 20) }
    }

    @Test
    fun `fetchCharacters passes through the correct offset for different page numbers`() = runBlocking {
        subject.fetchCharacters(pageNumber = 3)
        coVerify { marvelService.getCharacters(offset = 40) }

        subject.fetchCharacters(pageNumber = 2)
        coVerify { marvelService.getCharacters(offset = 20) }
    }

    @Test
    fun `fetchCharacters should return an empty list when the response is not successful`() =
        runBlocking<Unit> {
            coEvery { marvelService.getCharacters() } returns mockk {
                every { isSuccessful } returns false
            }

            val characters = subject.fetchCharacters()

            characters shouldBeEqualTo emptyList()
        }

    @Test
    fun `fetchCharacters should return an empty list when the response is successful but the data is null`() = runBlocking<Unit> {
        coEvery { marvelService.getCharacters() } returns mockk {
            every { isSuccessful } returns true
            every { body() } returns CharacterDataWrapper(
                code = 200, status = "SUCCESS", data = null, attributionText = null
            )
        }

        val characters = subject.fetchCharacters()

        characters shouldBeEqualTo emptyList()
    }

    @Test
    fun `fetchCharacters should return an empty list when the response is successful but the results are null`() = runBlocking<Unit> {
        coEvery { marvelService.getCharacters() } returns mockk {
            every { isSuccessful } returns true
            every { body() } returns CharacterDataWrapper(
                code = 200, status = "SUCCESS", data = CharacterDataContainer(results = null), attributionText = null
            )
        }

        val characters = subject.fetchCharacters()

        characters shouldBeEqualTo emptyList()
    }

    @Test
    fun `fetchCharacters should return characters when the response is successful`() = runBlocking<Unit> {
        coEvery { marvelService.getCharacters() } returns mockk {
            every { isSuccessful } returns true
            every { body() } returns CharacterDataWrapper(
                code = 200, status = "SUCCESS", data = CharacterDataContainer(results = listOf(
                        CharacterResponse(
                            id = 1,
                            name = "Spider-Man",
                            urls = emptyList(),
                            thumbnail = MarvelThumbnail("http://example.com/spider-man", "jpg")
                        )
                )), attributionText = null
            )
        }

        val characters = subject.fetchCharacters()

        characters shouldHaveSize 1
        characters shouldContain CharacterResponse(
            id = 1,
            name = "Spider-Man",
            urls = emptyList(),
            thumbnail = MarvelThumbnail("http://example.com/spider-man", "jpg")
        )
    }
}