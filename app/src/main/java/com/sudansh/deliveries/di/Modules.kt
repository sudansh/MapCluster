package com.sudansh.deliveries.di

import com.sudansh.deliveries.ui.MapsViewModel
import org.koin.android.architecture.ext.viewModel
import org.koin.dsl.module.applicationContext


val appModule = applicationContext {

    viewModel { MapsViewModel(get()) }
}