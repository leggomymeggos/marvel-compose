package com.leggomymeggos.marvelcompose.ui.main

import com.airbnb.mvrx.test.MvRxTestExtension
import com.leggomymeggos.marvelcompose.data.Character
import com.leggomymeggos.marvelcompose.data.CharacterUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.amshove.kluent.shouldBeEqualTo
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.RegisterExtension

class CharacterListViewModelTest {
    private val characterUseCase = mockk<CharacterUseCase> {
        coEvery { getCharacters() } returns emptyList()
    }

    private lateinit var subject: CharacterListViewModel

    companion object {
        @JvmField
        @RegisterExtension
        val mvrxTestExtension = MvRxTestExtension()
    }

    @BeforeEach
    fun setup() {
        subject = CharacterListViewModel(State(currentPage = 2), characterUseCase)
    }

    @Test
    fun `initialization fetches characters`() {
        coVerify { characterUseCase.getCharacters() }
    }

    @Test
    fun `when LoadNextPage is dispatched, more characters are loaded and the current page number is increased`() =
        runBlocking<Unit> {
            coEvery {
                characterUseCase.getCharacters(any())
            } returns listOf(
                Character(id = 1, name = "Spider-Man", thumbnailUrl = null)
            ) andThen listOf(
                Character(id = 2, name = "Venom", thumbnailUrl = null)
            )

            subject.dispatch(CharacterAction.LoadNextPage)
            coVerify { characterUseCase.getCharacters(pageNumber = 3) }
            val firstState = subject.awaitState()

            firstState.characterList shouldBeEqualTo listOf(Character(id = 1, name = "Spider-Man", thumbnailUrl = null))
            firstState.currentPage shouldBeEqualTo 3

            subject.dispatch(CharacterAction.LoadNextPage)
            coVerify { characterUseCase.getCharacters(pageNumber = 4) }
            val secondState = subject.awaitState()

            secondState.characterList shouldBeEqualTo listOf(
                Character(id = 1, name = "Spider-Man", thumbnailUrl = null),
                Character(id = 2, name = "Venom", thumbnailUrl = null)
            )
            secondState.currentPage shouldBeEqualTo 4
        }
}
