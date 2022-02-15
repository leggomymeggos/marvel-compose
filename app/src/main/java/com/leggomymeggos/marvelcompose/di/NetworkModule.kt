package com.leggomymeggos.marvelcompose.di

import com.airbnb.mvrx.hilt.MavericksViewModelComponent
import com.leggomymeggos.marvelcompose.network.ApiKeyInterceptor
import com.leggomymeggos.marvelcompose.network.MarvelService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

@Module
@InstallIn(MavericksViewModelComponent::class)
object NetworkModule {

    @Provides
    fun provideHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(ApiKeyInterceptor())
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .build()
    }

    @Provides
    fun provideMarvelService(client: OkHttpClient): MarvelService {
        return Retrofit.Builder()
            .client(client)
            .baseUrl("https://gateway.marvel.com:443")
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create(MarvelService::class.java)
    }
}