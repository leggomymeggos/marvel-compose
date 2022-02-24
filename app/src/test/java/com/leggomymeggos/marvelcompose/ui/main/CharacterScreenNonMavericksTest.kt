package com.leggomymeggos.marvelcompose.ui.main

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.MutableStateFlow
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(
    instrumentedPackages = ["androidx.loader.content"],
    sdk = [28]
)
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
}