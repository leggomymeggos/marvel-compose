package com.leggomymeggos.marvelcompose.ui.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.leggomymeggos.marvelcompose.data.Character
import io.mockk.*
import org.junit.Rule
import org.junit.Test

class CharacterListViewModelTest {
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private val subject = CharacterListViewModel()

    private val observer = mockk<Observer<List<*>>> {
        every { onChanged(any()) } just runs
    }

    @Test
    fun `data returns a list of characters`() {
        subject.data.observeForever(observer)

        verify {
            observer.onChanged(
                listOf(
                    Character(
                        id = 1,
                        name = "Spider-Man",
                        favorite = true
                    ),
                    Character(
                        id = 2,
                        name = "Venom",
                        favorite = false
                    ),
                    Character(
                        id = 3,
                        name = "Iron Man",
                        favorite = false
                    ),
                    Character(
                        id = 4,
                        name = "Thor",
                        favorite = true
                    ),
                    Character(
                        id = 5,
                        name = "Scarlet Witch",
                        favorite = true
                    ),
                    Character(
                        id = 6,
                        name = "Photon",
                        favorite = false
                    ),
                )
            )
        }
    }

    @Test
    fun `toggleFavorite can favorite the given character`() {
        subject.data.observeForever(observer)

        subject.toggleFavorite(2)

        verify {
            observer.onChanged(
                listOf(
                    Character(
                        id = 1,
                        name = "Spider-Man",
                        favorite = true
                    ),
                    Character(
                        id = 2,
                        name = "Venom",
                        favorite = true
                    ),
                    Character(
                        id = 3,
                        name = "Iron Man",
                        favorite = false
                    ),
                    Character(
                        id = 4,
                        name = "Thor",
                        favorite = true
                    ),
                    Character(
                        id = 5,
                        name = "Scarlet Witch",
                        favorite = true
                    ),
                    Character(
                        id = 6,
                        name = "Photon",
                        favorite = false
                    ),
                )
            )
        }
    }

    @Test
    fun `toggleFavorite can un-favorite the given character`() {
        subject.data.observeForever(observer)

        subject.toggleFavorite(4)

        verify {
            observer.onChanged(
                listOf(
                    Character(
                        id = 1,
                        name = "Spider-Man",
                        favorite = true
                    ),
                    Character(
                        id = 2,
                        name = "Venom",
                        favorite = false
                    ),
                    Character(
                        id = 3,
                        name = "Iron Man",
                        favorite = false
                    ),
                    Character(
                        id = 4,
                        name = "Thor",
                        favorite = false
                    ),
                    Character(
                        id = 5,
                        name = "Scarlet Witch",
                        favorite = true
                    ),
                    Character(
                        id = 6,
                        name = "Photon",
                        favorite = false
                    ),
                )
            )
        }
    }
}