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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.airbnb.mvrx.compose.collectAsState
import com.airbnb.mvrx.compose.mavericksViewModel
import com.leggomymeggos.marvelcompose.R
import com.leggomymeggos.marvelcompose.data.Character
import com.leggomymeggos.marvelcompose.ui.components.CenterCircleProgressIndicator

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CharacterScreen(viewModel: CharacterListViewModel = mavericksViewModel()) {
    val state by viewModel.collectAsState()
    val characterList = state.characterList

    if (characterList.isEmpty()) {
        CenterCircleProgressIndicator(modifier = Modifier.testTag("progressBar"))
    } else {
        LazyVerticalGrid(cells = GridCells.Adaptive(minSize = 128.dp)) {
            items(characterList) { CharacterContent(character = it) }
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
            .height(164.dp)
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