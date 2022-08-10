package com.leggomymeggos.marvelcompose.ui.main

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.airbnb.mvrx.compose.collectAsState
import com.airbnb.mvrx.compose.mavericksViewModel
import com.leggomymeggos.marvelcompose.R
import com.leggomymeggos.marvelcompose.data.Character
import com.leggomymeggos.marvelcompose.ui.components.CenterCircleProgressIndicator

@Composable
fun CharacterScreen(viewModel: CharacterListViewModel = mavericksViewModel()) {
    val state by viewModel.collectAsState()

    CharacterGrid(state.characterList)
}

@Composable
fun CharacterScreenNonMavericks(listViewModel: NonMavericksCharacterListViewModel = viewModel()) {
    val state by listViewModel.state.collectAsState()

    CharacterGrid(state.characterList)
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CharacterGrid(characterList: List<Character>) {
    if (characterList.isEmpty()) {
        CenterCircleProgressIndicator(modifier = Modifier.testTag("progressBar"))
    } else {
        // This allows the cards to grow as the font scale grows, which will
        // enable users needing a higher font size to still read the character names
        val fontScale = LocalContext.current.resources.configuration.fontScale
        val minCardWidth = fontScale * 128f
        val cardHeight = fontScale * 164f

        LazyVerticalGrid(cells = GridCells.Adaptive(minSize = minCardWidth.dp)) {
            items(characterList) { CharacterContent(character = it, modifier = Modifier.height(cardHeight.dp)) }
        }
    }
}

@OptIn(ExperimentalCoilApi::class)
@Composable
fun CharacterContent(character: Character, modifier: Modifier = Modifier) {
    Card(
        shape = RoundedCornerShape(4.dp),
        elevation = 4.dp,
        modifier = modifier
            .padding(8.dp)
            .fillMaxWidth()
    ) {
        Column {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.75f)
            ) {
                Image(
                    painter = rememberImagePainter(
                        data = character.thumbnailUrl ?: R.drawable.ic_marvel_logo
                    ),
                    contentScale = determineContentScale(character.thumbnailUrl),
                    contentDescription = character.name,
                    modifier = Modifier
                        .fillMaxHeight()
                        .fillMaxWidth()
                )
            }
            CenteredOneLineText(text = character.name)
        }
    }
}

private fun determineContentScale(thumbnailUrl: String?): ContentScale {
    return when {
        thumbnailUrl == null -> ContentScale.Inside
        thumbnailUrl.contains("not_available") -> ContentScale.FillBounds
        else -> ContentScale.Crop
    }
}

@Composable
fun CenteredOneLineText(text: String, modifier: Modifier = Modifier) {
    return Text(
        text = text,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
        textAlign = TextAlign.Center,
    )
}