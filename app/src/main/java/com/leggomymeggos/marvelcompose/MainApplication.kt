package com.leggomymeggos.marvelcompose

import android.app.Application
import android.content.Context
import coil.ImageLoader
import coil.ImageLoaderFactory
import coil.disk.DiskCache
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
            .diskCache(
                DiskCache.Builder()
                    .directory(applicationContext.cacheDir.resolve("image_cache"))
                    .maxSizePercent(0.02)
                    .build()
            )
            .build()
    }
}
