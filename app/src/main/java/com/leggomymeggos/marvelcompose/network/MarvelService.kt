package com.leggomymeggos.marvelcompose.network

import com.leggomymeggos.marvelcompose.network.data.CharacterDataWrapper
import retrofit2.Response
import retrofit2.http.GET

interface MarvelService {

    @GET("/v1/public/characters")
    suspend fun getCharacters() : Response<CharacterDataWrapper>
}