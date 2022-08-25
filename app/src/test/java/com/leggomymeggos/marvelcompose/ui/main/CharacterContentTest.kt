package com.leggomymeggos.marvelcompose.ui.main

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.leggomymeggos.marvelcompose.data.Character
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
class CharacterContentTest {

    @get:Rule
    val rule = createComposeRule()

    @Test
    fun `things are rendered on screen`() {
        rule.setContent {
            CharacterContent(
                character = Character(
                    id = 1,
                    name = "Spider-Man",
                    thumbnailUrl = null
                )
            )
        }

        rule.onNodeWithText("Spider-Man").assertExists().assertIsDisplayed()
        rule.onNodeWithText("Venom").assertDoesNotExist()
    }
}