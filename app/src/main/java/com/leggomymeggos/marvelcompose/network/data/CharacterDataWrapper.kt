package com.leggomymeggos.marvelcompose.network.data

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CharacterDataWrapper(
    val code: Int?,
    val status: String?,
    val data: CharacterDataContainer?,
    val attributionText: String?
)
