package com.sudansh.atm.di

import com.sudansh.atm.data.LiveDataCallAdapterFactory
import com.sudansh.atm.repository.remote.api.AtmApi
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val remoteModule = module {
	single { createWebService<AtmApi>() }
}

inline fun <reified T> createWebService(): T {
	return Retrofit.Builder()
		.baseUrl("http://207.154.210.145:8080/")
		.addConverterFactory(GsonConverterFactory.create())
		.addCallAdapterFactory(LiveDataCallAdapterFactory())
		.build().create(T::class.java)
}