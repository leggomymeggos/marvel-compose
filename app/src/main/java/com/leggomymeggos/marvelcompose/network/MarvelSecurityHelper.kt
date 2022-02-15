package com.leggomymeggos.marvelcompose.network

import com.leggomymeggos.marvelcompose.BuildConfig
import okhttp3.HttpUrl
import java.math.BigInteger
import java.security.MessageDigest
import java.util.*

object MarvelSecurityHelper {
    fun appendKeys(url: HttpUrl): HttpUrl {
        val timestamp = Date().time
        val hash = md5("$timestamp${BuildConfig.MARVEL_API_PRIVATEKEY}${BuildConfig.MARVEL_API_PUBLICKEY}")

        return url.newBuilder()
            .addQueryParameter("ts", timestamp.toString())
            .addQueryParameter("apikey", BuildConfig.MARVEL_API_PUBLICKEY)
            .addQueryParameter("hash", hash)
            .build()
    }

    private fun md5(str: String): String {
        val md = MessageDigest.getInstance("MD5")
        return BigInteger(1, md.digest(str.toByteArray())).toString(16).padStart(32, '0')
    }
}