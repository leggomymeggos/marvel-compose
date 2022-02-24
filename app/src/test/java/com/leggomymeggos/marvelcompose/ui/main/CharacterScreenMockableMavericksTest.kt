package com.leggomymeggos.marvelcompose.ui.main

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.leggomymeggos.marvelcompose.MavericksTestActivity
import com.leggomymeggos.marvelcompose.MavericksTestApplication
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(
    instrumentedPackages = ["androidx.loader.content"],
    sdk = [28],
    application = MavericksTestApplication::class
)
class CharacterScreenMockableMavericksTest {

    @get:Rule
    val rule = createAndroidComposeRule<MavericksTestActivity>()

    @Test
    fun `when the character list is empty then a progress bar should be shown`() {
        rule.onNodeWithText("Empty list").performClick()
        rule.onNodeWithTag("progressBar").assertExists()
    }
}
