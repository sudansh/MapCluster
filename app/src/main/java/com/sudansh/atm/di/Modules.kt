package com.sudansh.atm.di

import com.sudansh.atm.ui.MapsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
	viewModel { MapsViewModel(get()) }
}