package com.leggomymeggos.marvelcompose.data

import com.leggomymeggos.marvelcompose.network.MarvelSecurityHelper
import com.leggomymeggos.marvelcompose.network.data.CharacterResponse
import com.leggomymeggos.marvelcompose.network.data.MarvelThumbnail
import io.mockk.*
import kotlinx.coroutines.runBlocking
import okhttp3.HttpUrl.Companion.toHttpUrl
import org.amshove.kluent.shouldContain
import org.amshove.kluent.shouldContainAll
import org.amshove.kluent.shouldHaveSize
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class CharacterUseCaseTest {
    private val characterRepository = mockk<CharacterRepository> {
        coEvery { fetchCharacters() } returns emptyList()
    }
    private val subject = CharacterUseCase(characterRepository)

    @BeforeEach
    fun setup() {
        mockkObject(MarvelSecurityHelper)
        every { MarvelSecurityHelper.appendKeys(any()) } returns mockk {
            every { this@mockk.toString() } returns "secure-thumbnail-url"
        }
    }

    @AfterEach
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun `getCharacters gets characters from the repository`() = runBlocking<Unit> {
        subject.getCharacters()

        coVerify { characterRepository.fetchCharacters() }
    }

    @Test
    fun `getCharacters returns mapped characters`() = runBlocking<Unit> {
        coEvery { characterRepository.fetchCharacters() } returns listOf(
            CharacterResponse(id = 1, name = "Spider-Man", urls = listOf(), thumbnail = MarvelThumbnail(path = "http://example.com/spider-man", extension = "jpg")),
            CharacterResponse(id = 2, name = "Thor", urls = listOf(), thumbnail = MarvelThumbnail(path = "http://example.com/thor", extension = "jpg")),
        )

        val characters = subject.getCharacters()

        verify { MarvelSecurityHelper.appendKeys("http://example.com/spider-man.jpg".toHttpUrl()) }
        verify { MarvelSecurityHelper.appendKeys("http://example.com/thor.jpg".toHttpUrl()) }

        characters shouldHaveSize 2
        characters shouldContainAll listOf(
            Character(id = 1, name = "Spider-Man", thumbnailUrl = "secure-thumbnail-url"),
            Character(id = 2, name = "Thor", thumbnailUrl = "secure-thumbnail-url")
        )
    }

    @Test
    fun `getCharacters returns defaults if fields are null`() = runBlocking<Unit> {
        coEvery { characterRepository.fetchCharacters() } returns listOf(
            CharacterResponse(id = null, name = null, urls = listOf(), thumbnail = null)
        )

        val characters = subject.getCharacters()

        verify { MarvelSecurityHelper.appendKeys(any()) wasNot called }

        characters shouldHaveSize 1
        characters shouldContain Character(id = -1, name = "", thumbnailUrl = null)
    }
}