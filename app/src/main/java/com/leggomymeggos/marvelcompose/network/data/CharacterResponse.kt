package com.leggomymeggos.marvelcompose.network.data

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CharacterResponse(
    val id: Int?,
    val name: String?,
    val urls: List<MarvelUrl>?,
    val thumbnail: MarvelThumbnail?
)

@JsonClass(generateAdapter = true)
data class MarvelUrl(
    val type: String,
    val url: String
)

@JsonClass(generateAdapter = true)
data class MarvelThumbnail(
    val path: String,
    val extension: String
)