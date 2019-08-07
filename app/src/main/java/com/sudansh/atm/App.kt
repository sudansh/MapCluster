package com.sudansh.atm

import android.app.Application
import com.sudansh.atm.di.appModule
import com.sudansh.atm.di.localModule
import com.sudansh.atm.di.remoteModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {

	override fun onCreate() {
		super.onCreate()
		startKoin {
			// declare used Android context
			androidContext(this@App)
			// declare modules
			modules(listOf(appModule, remoteModule, localModule))
		}

	}

}