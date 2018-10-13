package com.sudansh.atm.di

import com.sudansh.atm.ui.MapsViewModel
import org.koin.android.architecture.ext.viewModel
import org.koin.dsl.module.applicationContext


val appModule = applicationContext {

    viewModel { MapsViewModel(get()) }
}