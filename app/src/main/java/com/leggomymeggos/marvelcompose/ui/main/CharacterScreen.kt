package com.leggomymeggos.marvelcompose.ui.main

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.leggomymeggos.marvelcompose.R
import com.leggomymeggos.marvelcompose.ui.Colors
import com.leggomymeggos.marvelcompose.data.Character

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CharacterScreen(characterViewModel: CharacterListViewModel = viewModel()) {
    val characters: List<Character> by characterViewModel.data.observeAsState(listOf())

    LazyVerticalGrid(
        cells = GridCells.Adaptive(minSize = 128.dp),
    ) {
        items(characters) { character ->
            CharacterContent(
                character = character,
                onFavorite = { clickedCharacter ->
                    characterViewModel.toggleFavorite(
                        clickedCharacter.id
                    )
                }
            )
        }
    }
}

@Composable
fun CharacterContent(character: Character, onFavorite: (character: Character) -> Unit) {
    Card(
        shape = RoundedCornerShape(4.dp),
        elevation = 4.dp,
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .fillMaxHeight()
    ) {
        Column(
            modifier = Modifier.padding(4.dp),
        ) {
            Text(
                text = character.name,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
            IconButton(
                modifier = Modifier.align(alignment = Alignment.End),
                onClick = { onFavorite(character) }
            ) {
                if (character.favorite) {
                    FilledStarIcon()
                } else OutlineStarIcon()
            }
        }
    }
}

@Composable
fun OutlineStarIcon() = Icon(
    painter = painterResource(id = R.drawable.ic_star_outline_24),
    tint = Color.LightGray,
    contentDescription = "Not Favorited"
)

@Composable
fun FilledStarIcon() = Icon(
    painter = painterResource(id = R.drawable.ic_star_filled_24),
    tint = Colors.Gold,
    contentDescription = "Favorited"
)
