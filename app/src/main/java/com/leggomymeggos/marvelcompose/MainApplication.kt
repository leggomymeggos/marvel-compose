package com.leggomymeggos.marvelcompose

import android.app.Application
import coil.ImageLoader
import coil.ImageLoaderFactory
import coil.util.CoilUtils
import com.airbnb.mvrx.Mavericks
import com.airbnb.mvrx.mocking.MockableMavericks
import dagger.hilt.android.HiltAndroidApp
import okhttp3.OkHttpClient

@HiltAndroidApp
open class MainApplication : Application(), ImageLoaderFactory {
    override fun onCreate() {
        super.onCreate()
        MockableMavericks.initialize(this)
    }

    override fun newImageLoader(): ImageLoader {
        return ImageLoader.Builder(applicationContext)
            .okHttpClient {
                OkHttpClient.Builder()
                    .cache(CoilUtils.createDefaultCache(applicationContext))
                    .build()
            }
            .build()
    }
}
