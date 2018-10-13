package com.sudansh.deliveries.di

import android.arch.persistence.room.Room
import com.sudansh.deliveries.data.AppExecutors
import com.sudansh.deliveries.repository.DeliveryRepository
import com.sudansh.deliveries.repository.local.db.AppDatabase
import org.koin.dsl.module.applicationContext


val localModule = applicationContext {
    bean { AppExecutors() }
    bean { DeliveryRepository(get(), get(), get()) }
    bean {
        Room.databaseBuilder(get(), AppDatabase::class.java, "atm-db").build()
    }
    bean { get<AppDatabase>().deliveryDao() }
}