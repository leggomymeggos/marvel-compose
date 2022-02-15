package com.leggomymeggos.marvelcompose.ui.main

import com.airbnb.mvrx.test.MvRxTestExtension
import com.leggomymeggos.marvelcompose.data.CharacterUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.RegisterExtension

class CharacterListViewModelTest {
    private val characterUseCase = mockk<CharacterUseCase> {
        coEvery { getCharacters() } returns emptyList()
    }

    companion object {
        @JvmField
        @RegisterExtension
        val mvrxTestExtension = MvRxTestExtension()
    }

    @Test
    fun `initialization fetches characters`() {
        CharacterListViewModel(State(), characterUseCase)
        coVerify { characterUseCase.getCharacters() }
    }
}
