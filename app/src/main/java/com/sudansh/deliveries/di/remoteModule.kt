package com.sudansh.deliveries.di

import com.sudansh.deliveries.data.LiveDataCallAdapterFactory
import com.sudansh.deliveries.repository.remote.api.DeliverApi
import okhttp3.OkHttpClient
import org.koin.dsl.module.applicationContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val remoteModule = applicationContext {
    bean { createOkHttpClient() }
    bean { createWebService<DeliverApi>(get()) }
}

fun createOkHttpClient(): OkHttpClient {
    return OkHttpClient.Builder().apply {
        connectTimeout(60L, TimeUnit.SECONDS)
        readTimeout(60L, TimeUnit.SECONDS)
    }.build()
}

inline fun <reified T> createWebService(okHttpClient: OkHttpClient): T {
    val retrofit = Retrofit.Builder()
            .baseUrl("http://207.154.210.145:8080/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(LiveDataCallAdapterFactory())
            .build()
    return retrofit.create(T::class.java)
}