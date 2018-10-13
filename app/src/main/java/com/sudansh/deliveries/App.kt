package com.sudansh.deliveries

import android.app.Application
import com.sudansh.deliveries.di.appModule
import com.sudansh.deliveries.di.localModule
import com.sudansh.deliveries.di.remoteModule
import org.koin.android.ext.android.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin(listOf(appModule, remoteModule, localModule))
    }

}