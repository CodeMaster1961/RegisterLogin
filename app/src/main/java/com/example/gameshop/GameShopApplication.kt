package com.example.gameshop

import android.app.Application
import com.example.gameshop.data.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class GameShopApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@GameShopApplication)
            modules(appModule)
        }
    }
}