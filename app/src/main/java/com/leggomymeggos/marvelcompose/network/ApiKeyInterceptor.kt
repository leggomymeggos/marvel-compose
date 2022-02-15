package com.leggomymeggos.marvelcompose.network

import okhttp3.Interceptor
import okhttp3.Response

class ApiKeyInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()

        val url = MarvelSecurityHelper.appendKeys(originalRequest.url)
        val request = originalRequest.newBuilder().url(url).build()

        return chain.proceed(request)
    }
}
