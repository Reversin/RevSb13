package com.revsb_11

import android.app.Application
import com.revsb_11.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class RevSbApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@RevSbApplication)
            modules(appModule)
        }
    }
}