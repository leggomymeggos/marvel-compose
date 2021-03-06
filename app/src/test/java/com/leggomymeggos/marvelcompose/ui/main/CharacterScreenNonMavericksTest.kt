package com.leggomymeggos.marvelcompose.ui.main

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import com.leggomymeggos.marvelcompose.data.Character
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.MutableStateFlow
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(instrumentedPackages = ["androidx.loader.content"], sdk = [28])
class CharacterScreenNonMavericksTest {
    @get:Rule
    val rule = createComposeRule()

    private val stateFlow = MutableStateFlow(State())
    private val viewModel = mockk<NonMavericksCharacterListViewModel> {
        every { state } returns stateFlow
    }

    @Test
    fun `when the character list is empty then a progress bar is shown`() {
        rule.setContent {
            CharacterScreenNonMavericks(viewModel)
        }

        rule.onNodeWithTag("progressBar").assertExists()
    }

    @Test
    fun `when the character list is loaded, then the characters are shown`() {
        rule.setContent { CharacterScreenNonMavericks(viewModel) }

        stateFlow.compareAndSet(stateFlow.value, State(listOf(
            Character(id = 1, name = "Spider-Man", thumbnailUrl = null),
            Character(id = 2, name = "Venom", thumbnailUrl = null)
        )))

        rule.onNodeWithText("Spider-Man").assertIsDisplayed()
        rule.onNodeWithText("Venom").assertIsDisplayed()
        rule.onNodeWithTag("progressBar").assertDoesNotExist()
    }
}
