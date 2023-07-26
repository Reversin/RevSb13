package com.example.revsb_11

import android.app.Application
import com.example.revsb_11.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class RevSb : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@RevSb)
            modules(appModule)
        }
    }
}