package com.leggomymeggos.marvelcompose.network.data

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CharacterDataContainer(val results: List<CharacterResponse>?)