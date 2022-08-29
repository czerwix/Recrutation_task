package com.mobeedev.vama

import android.app.Application
import com.mobeedev.vama.album.di.AlbumModule
import com.mobeedev.vama.di.AppModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class VamaAplication : Application() {

    override fun onCreate() {
        super.onCreate()

        setUpKoin()
    }

    private fun setUpKoin() {
        val modules = AppModule()
        startKoin {
            androidLogger(Level.NONE)
            androidContext(this@VamaAplication)
            modules(
                modules.dataModule,
                modules.configModule,
                modules.remoteModule,
                modules.apiModule,
                modules.moshiModule
            )

            AlbumModule.load()
        }
    }
}