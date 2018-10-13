package com.sudansh.atm

import android.app.Application
import com.sudansh.atm.di.appModule
import com.sudansh.atm.di.localModule
import com.sudansh.atm.di.remoteModule
import org.koin.android.ext.android.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin(listOf(appModule, remoteModule, localModule))
    }

}