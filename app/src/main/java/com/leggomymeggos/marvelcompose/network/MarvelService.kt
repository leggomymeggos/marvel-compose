package com.leggomymeggos.marvelcompose.network

import com.leggomymeggos.marvelcompose.data.CharacterRepository.Companion.DEFAULT_PAGE_SIZE
import com.leggomymeggos.marvelcompose.network.data.CharacterDataWrapper
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MarvelService {

    @GET("/v1/public/characters")
    suspend fun getCharacters(
        @Query("offset") offset: Int = 0,
        @Query("limit") limit: Int = DEFAULT_PAGE_SIZE,
    ) : Response<CharacterDataWrapper>
}