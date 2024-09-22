package com.pankajjangid.cryptotest.utils

import android.app.Application
import com.pankajjangid.cryptotest.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin
import org.koin.core.context.GlobalContext.stopKoin

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        // Initialize Koin
        startKoin {
            androidLogger()
            androidContext(this@App)
            modules(appModule)
        }
    }
    override fun onTerminate() {
        super.onTerminate()
        stopKoin()
    }
}